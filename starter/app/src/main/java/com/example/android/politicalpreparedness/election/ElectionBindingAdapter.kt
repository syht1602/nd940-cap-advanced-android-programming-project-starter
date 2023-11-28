package com.example.android.politicalpreparedness.election

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.utils.DATE_FORMAT_YYYY_MM_DD
import com.example.android.politicalpreparedness.utils.parseDate
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale


@BindingAdapter("electionDay")
fun electionDay(view: TextView, election: Election?) {
    view.text = election?.electionDay?.parseDate(DATE_FORMAT_YYYY_MM_DD)
}
