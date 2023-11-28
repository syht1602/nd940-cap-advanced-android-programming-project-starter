package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class RepresentativeFragment : Fragment() {

    private lateinit var binding: FragmentRepresentativeBinding
    private val viewModel: RepresentativeViewModel by viewModel()
    private var permissionDialog: AlertDialog? = null
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getLocation()
            } else {
                showRequestPermissionDialog()
            }
        }

    companion object {
        //TODO: Add Constant for Location request
    }

    //TODO: Declare ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //TODO: Establish bindings
        binding = FragmentRepresentativeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        //TODO: Define and assign Representative adapter
        val representativeAdapter = RepresentativeListAdapter()
        binding.rvRepresentative.adapter = representativeAdapter
        viewModel.representatives.observe(viewLifecycleOwner) {
            representativeAdapter.submitList(it)
        }

        //TODO: Populate Representative adapter

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.states,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.state.adapter = it
        }
        //TODO: Establish button listeners for field and location search

        binding.buttonLocation.setOnClickListener {
            hideKeyboard()
            if (isPermissionGranted()) {
                getLocation()
            } else {
                enableLocationPermissions()
            }
        }
        return binding.root
    }

    private fun isPermissionGranted(): Boolean {
        //TODO: Check if permission is already granted and return (true = granted, false = denied/other)
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        //TODO: Get location from LocationServices
        //TODO: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val provider = locationManager.getBestProvider(Criteria(), true)
        provider?.let {
            val location = locationManager.getLastKnownLocation(it)
            if (location != null) {
                // get address from location
                geoCodeLocation(location).let { geoAddress ->
                    var address = Address(
                        line1 = "1600 Pennsylvania Avenue Northwest",
                        city = "Washington",
                        state = "DC",
                        zip = "20500"
                    )
                    if (geoAddress != null) {
                        //Mock for testing if address cannot get with provided lat long
                        address = geoAddress
                    }
                    viewModel.setAddress(address)
                    viewModel.getRepresentativesOfficials()
                }
            }
        }
    }

    private fun geoCodeLocation(location: Location): Address? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
            ?.map { address ->
                if (address.thoroughfare.isNullOrEmpty() || address.locality.isNullOrEmpty() || address.adminArea.isNullOrEmpty() || address.postalCode.isNullOrEmpty()) {
                    return null
                }
                Address(
                    address.thoroughfare,
                    address.subThoroughfare,
                    address.locality,
                    address.adminArea,
                    address.postalCode
                )
            }
            ?.firstOrNull()
    }

    private fun enableLocationPermissions() {
        when {
            isPermissionGranted() -> {
                getLocation()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showRequestPermissionDialog()
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }

    private fun showRequestPermissionDialog() {
        if (permissionDialog == null) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.location_required_title))
            builder.setMessage(getString(R.string.location_required_message))

            builder.setPositiveButton(R.string.settings) { dialog, _ ->
                openPermissionSetting()
                dialog.dismiss()
            }

            builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
                requireActivity().finish()
            }
            permissionDialog = builder.show()
        } else {
            permissionDialog?.show()
        }
    }

    private fun openPermissionSetting() {
        val intent = Intent()
        val action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri: Uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.action = action
        intent.data = uri
        requireActivity().startActivity(intent)
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}