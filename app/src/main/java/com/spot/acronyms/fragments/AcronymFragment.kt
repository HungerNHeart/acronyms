package com.spot.acronyms.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.spot.acronyms.adapters.AcronymsAdapter
import com.spot.acronyms.viewmodels.AcronymsViewModel
import com.spot.acronyms.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint

import com.spot.acronyms.services.initiateNetworkListener
import com.spot.acronyms.utils.hideSoftKeyboard
import com.spot.acronyms.utils.setClearIcon


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class AcronymFragment : Fragment() {

    private val TAG = this::class.java.simpleName

    private val viewModel: AcronymsViewModel by viewModels()

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter = AcronymsAdapter().apply {
        openSettings = {
            _binding?.root?.hideSoftKeyboard()
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
            .apply {
                this.viewModel = this@AcronymFragment.viewModel
                this.lifecycleOwner = viewLifecycleOwner
                list.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = this@AcronymFragment.adapter
                }
                searchView.setClearIcon()
            }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext().initiateNetworkListener(){
            viewModel.onNetworkStatusChanged(it)
        }
        viewModel.apply {
            resource.observe(viewLifecycleOwner) {
                it?.let { resource ->
                    adapter.setData(resource.data ?: mutableListOf())
                }
            }
        }
        viewModel._showNetworkStatus.observe(viewLifecycleOwner){
            adapter.updateExtraRow(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}