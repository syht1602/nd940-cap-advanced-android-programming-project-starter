package com.example.android.politicalpreparedness.network.models

import androidx.lifecycle.LiveData
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class ElectionByFollowing(
    @Embedded val elections: Election?,
    @Relation(
        parentColumn = "id",
        entityColumn = "electionId",
    )
    val following: Following,
)
