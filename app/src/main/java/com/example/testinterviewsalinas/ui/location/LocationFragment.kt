package com.example.testinterviewsalinas.ui.location

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.testinterviewsalinas.MainActivity
import com.example.testinterviewsalinas.R
import com.example.testinterviewsalinas.databinding.FragmentLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class LocationFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentLocationBinding? = null
    private lateinit var mMap: GoogleMap
    @Inject
    lateinit var locationViewModel: LocationViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).testComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationViewModel.getLocations()

        locationViewModel.locationsListData.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty())
            {
                it.forEach{
                    mMap.addMarker(MarkerOptions().position(LatLng(it.latitude!!,it.longitude!!)).title(it.date?.toDate().toString()))
                }
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(LatLng(it.first().latitude!!,it.first().longitude!!),16f),
                    3000,
                    null
                )
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}