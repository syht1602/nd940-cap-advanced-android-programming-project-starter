package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class VoterInfoFragment : Fragment() {
    private val viewModel: VoterInfoViewModel by viewModel()
    private lateinit var binding: FragmentVoterInfoBinding
    private val args: VoterInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View? {

        // TODO: Add ViewModel values and create ViewModel

        // TODO: Add binding values

        // TODO: Populate voter info -- hide views without provided data.

        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */

        // TODO: Handle loading of URLs

        // TODO: Handle save button UI state
        // TODO: cont'd Handle save button clicks
//        return null
        binding = FragmentVoterInfoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.getElectionById(args.argElectionId)
        viewModel.getFollowingStatus(args.argElectionId)
        return binding.root
    }

    // TODO: Create method to load URL intents
}