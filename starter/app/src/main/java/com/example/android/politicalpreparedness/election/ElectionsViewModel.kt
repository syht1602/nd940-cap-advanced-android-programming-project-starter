package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(private val dao: ElectionDao) : ViewModel() {

    //TODO: Create live data val for upcoming elections

    //TODO: Create live data val for saved elections

    private val _electionList = MutableLiveData<List<Election>>()
    val electionList: LiveData<List<Election>>
        get() = _electionList

    private val _electionFollowingList = MutableLiveData<List<Election?>>()
    val electionFollowingList: LiveData<List<Election?>>
        get() = _electionFollowingList

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    fun refresh() {
        getListElection()
        getFollowingElections()
    }


    private fun getListElection() {
        viewModelScope.launch {
            try {
                val elections = CivicsApi.retrofitService.getElections().elections
                Timber.e("Response: ${elections}")
                if (elections.isEmpty()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        _electionList.value = dao.getAllElection()
                    }
                } else {
                    _electionList.value = elections
                    viewModelScope.launch(Dispatchers.IO) {
                        elections.forEach {
                            dao.insertElection(it)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getFollowingElections() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val electionByFollowingList = mutableListOf<Election?>()
                dao.getElectionByFollowing().forEach {
                    if (it.following.isFollowing != null && it.following.isFollowing) {
                        electionByFollowingList.add(it.elections)
                    }
                }
                if (electionByFollowingList.isEmpty()) {
                    return@launch
                }
                _electionFollowingList.postValue(electionByFollowingList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info

}