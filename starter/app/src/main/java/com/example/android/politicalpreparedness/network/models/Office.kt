package com.example.android.politicalpreparedness.network.models

import android.os.Parcelable
import com.example.android.politicalpreparedness.representative.model.Representative
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Office(
    val name: String,
    @Json(name = "divisionId") val division: String,
    @Json(name = "officialIndices") val officials: List<Int>
) : Parcelable {
    fun getRepresentatives(officials: List<Official>): List<Representative> {
        return this.officials.map { index ->
            Representative(officials[index], this)
        }
    }
}
