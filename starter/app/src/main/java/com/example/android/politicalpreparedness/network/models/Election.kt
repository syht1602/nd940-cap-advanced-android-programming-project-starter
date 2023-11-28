package com.example.android.politicalpreparedness.network.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.Date

@Entity(tableName = "election_table")
data class Election(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String = "",
    val electionDay: String = "",
//        @ColumnInfo(name = "isFavorite") val isFavorite: Boolean = false,
    val ocdDivisionString: String = "",
)