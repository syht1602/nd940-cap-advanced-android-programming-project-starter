package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.models.Election
import org.koin.androidx.viewmodel.ext.android.viewModel

class ElectionsFragment : Fragment() {

    // TODO: Declare ViewModel
    private lateinit var binding: FragmentElectionBinding
    private val viewModel: ElectionsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // TODO: Add ViewModel values and create ViewModel

        // TODO: Add binding values
        binding = FragmentElectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val upcomingElectionsAdapter = ElectionListAdapter(ElectionListener {
            gotoVoterInfo(it)
        })
        binding.rvUpcoming.adapter = upcomingElectionsAdapter

        val followingElectionListAdapter = ElectionListAdapter(ElectionListener {
            gotoVoterInfo(it)
        })

        binding.rvSaved.adapter = followingElectionListAdapter

        with(viewModel) {
            electionList.observe(viewLifecycleOwner) {
                upcomingElectionsAdapter.submitList(it)
            }
            electionFollowingList.observe(viewLifecycleOwner) {
                followingElectionListAdapter.submitList(it)
            }
        }

        // TODO: Link elections to voter info

        // TODO: Initiate recycler adapters

        // TODO: Populate recycler adapters
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
        println("ElectionsFragment onResume")
    }

    // TODO: Refresh adapters when fragment loads

    private fun gotoVoterInfo(election: Election) {
        val electionsFragmentDirections =
            ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(
                election.id,
            )
        findNavController().navigate(electionsFragmentDirections)
    }
}