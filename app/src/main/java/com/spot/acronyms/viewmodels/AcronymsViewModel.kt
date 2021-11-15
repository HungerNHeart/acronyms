package com.spot.acronyms.viewmodels

import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import com.spot.acronyms.adapters.orEmpty
import com.spot.acronyms.repository.AcronymsRepository
import com.spot.acronyms.services.NetworkStatus
import com.spot.acronyms.services.Resource
import com.spot.acronyms.services.Status
import com.spot.acronyms.services.model.LongFormData
import com.spot.acronyms.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AcronymsViewModel @Inject constructor(
    private val repo: AcronymsRepository
) : ViewModel() {

    private val TAG = this::class.java.simpleName

    private val searchQuery = MutableLiveData<String>("")
    private val networkStatus = MutableLiveData<NetworkStatus>()
    private var searchJob: Job? = null
    val resource: LiveData<Resource<List<LongFormData>>> = switchMap(searchQuery) {
        liveData(Dispatchers.IO) {
            if (Validator.validateSearch(it.orEmpty())) {
                emit(Resource.loading(data = null))
                try {
                    val result = repo.getLongFrom(it.orEmpty())
                    val list = result.flatMap {
                        it.longForm
                    }
                    emit(Resource.success(data = list))
                } catch (e: Exception) {
                    emit(Resource.error(data = null, message = e.message))
                }
            } else {
                emit(Resource.emptyState())
            }
        }
    }
    val _showProgress: LiveData<Boolean> = map(resource) {
        it.status == Status.LOADING
    }
    val _showNetworkStatus: LiveData<Boolean> = map(networkStatus){
        it == NetworkStatus.DISCONNECTED
    }


    private fun doSearch(query: String, isRefresh: Boolean) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if(!isRefresh && searchQuery.value != query){
                delay(500)
                searchQuery.postValue(query)
            }else if(isRefresh){
                searchQuery.postValue(query)
            }
        }
    }

    fun doSearch(query: CharSequence?) {
        doSearch(query.orEmpty().toString().trim(), false)
    }

    fun onNetworkStatusChanged(status: NetworkStatus) {
        if(Validator.needRefresh(networkStatus.value, status)){
            refresh()
        }
        networkStatus.postValue(status)
    }

    fun refresh(){
        doSearch(query = searchQuery.value.orEmpty(), isRefresh = true)
    }
}