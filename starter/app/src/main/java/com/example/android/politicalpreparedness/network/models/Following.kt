package com.example.android.politicalpreparedness.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "following_table")
data class Following(
    @PrimaryKey(autoGenerate = false)
    val electionId: Int,
    val isFollowing: Boolean? = false,
)