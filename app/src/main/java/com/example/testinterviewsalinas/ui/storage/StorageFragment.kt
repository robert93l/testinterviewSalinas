package com.example.testinterviewsalinas.ui.storage

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testinterviewsalinas.MainActivity
import com.example.testinterviewsalinas.R
import com.example.testinterviewsalinas.adapters.StorageAdapter
import com.example.testinterviewsalinas.databinding.FragmentStorageBinding

import javax.inject.Inject

class StorageFragment : Fragment() {

    private var _binding: FragmentStorageBinding? = null
    @Inject
    lateinit var storageViewModel: StorageViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                binding.progresBar.visibility = View.VISIBLE
                if(it.data?.clipData != null)
                {
                    for(i in 0 until it.data!!.clipData!!.itemCount)
                    {
                        val uri: Uri = it.data!!.clipData!!.getItemAt(i).uri
                        storageViewModel.uploadImage(uri, requireContext())
                    }
                } else if (it.data?.data != null) {
                    val uriPath = it.data?.data
                    if (uriPath != null) {
                        storageViewModel.uploadImage(uriPath, requireContext())
                    }
                }
            }
        }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), R.string.succes_permission, Toast.LENGTH_SHORT).show()
                galleryAddPic()
            } else {
                Toast.makeText(requireContext(), R.string.succes_permission, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).testComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStorageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initRecycler()
        storageViewModel.getStorage()
        binding.progresBar.visibility = View.VISIBLE

        binding.fab.setOnClickListener{
            if(checkPermissionRequest())
                galleryAddPic()
            else
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        storageViewModel.uploadLiveData.observe(viewLifecycleOwner, Observer {
            if(it)
            {
                Toast.makeText(requireContext(), "Upload files success", Toast.LENGTH_SHORT).show()
                binding.progresBar.visibility = View.GONE
            }

            else{
                Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show()
                binding.progresBar.visibility = View.GONE
            }
        })

        storageViewModel.storageListData.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty())
            {
                binding.rvStorage.adapter = StorageAdapter(it)
                binding.progresBar.visibility = View.GONE
            }

            else
            {
                binding.progresBar.visibility = View.GONE
            }

        })

        return root
    }

    private fun galleryAddPic() {
        val intent  = Intent(Intent.ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        getResult.launch(intent)
    }

    private fun initRecycler(){
        binding.rvStorage.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    private fun checkPermissionRequest(): Boolean {
        val result = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}