package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.AdministrationBody
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.Following
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VoterInfoViewModel(private val dataSource: ElectionDao) : ViewModel() {

    //TODO: Add live data to hold voter info
    private val _election = MutableLiveData<Election>()
    val election: LiveData<Election>
        get() = _election

    //TODO: Add var and methods to populate voter info
    private val _voterInfo = MutableLiveData<AdministrationBody>()
    val voterInfo: LiveData<AdministrationBody>
        get() = _voterInfo

    //TODO: Add var and methods to support loading URLs

    var isFollowingElection = MutableLiveData<Boolean>()

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

    fun getElectionById(electionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val election = dataSource.getElectionById(electionId)
            election?.let {
                _election.postValue(it)
            }
        }
    }

    /**
     * Have not enough information to get the voter info, it's need the voter address but base on the flow cannot get the address here.
     */
    private fun getVoterInfo(address: String, electionId: Int) {
        viewModelScope.launch {
            try {
                CivicsApi.retrofitService.getVoterInfo(address, electionId).let {
                    _voterInfo.value = it.state?.firstOrNull()?.electionAdministrationBody
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getFollowingStatus(electionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val followingElection = dataSource.getFollowingByElectionId(electionId)
            println("getFollowingStatus = $followingElection}")
            isFollowingElection.postValue(
                if (followingElection?.isFollowing == null) {
                    false
                } else {
                    followingElection.isFollowing
                }
            )
        }
    }

    fun updateFollowingElectionStatus(electionId: Int) {
        isFollowingElection.value = !isFollowingElection.value!!
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.insertFollowing(Following(electionId, isFollowingElection.value!!))
        }
    }

}