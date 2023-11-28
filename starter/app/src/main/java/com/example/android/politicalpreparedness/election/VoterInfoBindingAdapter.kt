package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.example.android.politicalpreparedness.R


@BindingAdapter("setFollowButtonTitle")
fun setFollowButtonTitle(button: Button, status: Boolean) {
    println("setFollowButtonTitle status = $status")
    val title =
        button.context.getString(if (status) R.string.un_follow_election else R.string.follow_election)
    button.text = title
}

@BindingAdapter("onClickVoterInfoLink")
fun onClickVoterInfoLink(textView: TextView, url: String?) {
    textView.setOnClickListener {
        if (!url.isNullOrEmpty()) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            textView.context.startActivity(browserIntent)
        } else {
            Toast.makeText(
                textView.context,
                "Link is not available",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}