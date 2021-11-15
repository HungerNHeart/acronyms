package com.spot.acronyms.adapters

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.spot.acronyms.R
import com.spot.acronyms.services.Resource
import com.spot.acronyms.services.Status
import com.spot.acronyms.services.model.LongFormData
import com.spot.acronyms.utils.onDone

@BindingAdapter("android:text")
fun setIntText(view: TextView, value: Int){
    view.text = value.toString()
}


@BindingAdapter("android:showMessage")
fun showMessage(view: TextView, resource: Resource<List<LongFormData>>? = null){
    val message: String? = when(resource?.status){
        Status.SUCCESS -> if(resource.data.isNullOrEmpty()) view.context.getString(R.string.no_data) else ""
        Status.ERROR -> resource.message?:view.context.getString(R.string.something_went_wrong)
        Status.EMPTY_QUERY-> view.context.getString(R.string.empty_message)
        Status.NO_NETWORK_FOUND-> view.context.getString(R.string.no_network)
        else -> null
    }
    view.text = message.orEmpty()
}

@BindingAdapter("android:showDescription")
fun showDescription(view: TextView, resource: Resource<List<LongFormData>>? = null){
    val message: String? = when(resource?.status){
        Status.SUCCESS -> if(resource.data.isNullOrEmpty()) view.context.getString(R.string.no_data_description) else ""
        Status.EMPTY_QUERY-> view.context.getString(R.string.empty_message_description)
        else -> null
    }
    view.text = message.orEmpty()
}

@BindingAdapter("android:onDone")
fun performOnDone(view: EditText, onDone: ()->Unit){
    view.onDone {
        onDone.invoke()
    }
}

fun CharSequence?.orEmpty() = this ?: ""
fun String?.orEmpty() = this ?: ""