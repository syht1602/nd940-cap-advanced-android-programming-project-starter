package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepresentativeViewModel : ViewModel() {

    //TODO: Establish live data for representatives and address

    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>>
        get() = _representatives

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address

    //TODO: Create function to fetch representatives from API from a provided address

    fun getRepresentativesOfficials() {
        _representatives.value = listOf()
        viewModelScope.launch(Dispatchers.IO) {
            address.value?.toFormattedString()
                ?.let { address ->
                    println("Address formatted = $address")
                    val representativeList =
                        CivicsApi.retrofitService.getRepresentatives(address = address)
                    val official = mutableListOf<Representative>()
                    representativeList.offices.forEach {
                        official.addAll(it.getRepresentatives(representativeList.officials))
                    }
                    _representatives.postValue(official)
                }
        }
    }

    fun setAddress(address: Address) {
        this._address.value = address
    }

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

}
