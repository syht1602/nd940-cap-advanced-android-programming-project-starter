package com.example.android.politicalpreparedness.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionByFollowing
import com.example.android.politicalpreparedness.network.models.Following

@Dao
interface ElectionDao {

    //TODO: Add insert query

    //TODO: Add select all election query

    //TODO: Add select single election query

    //TODO: Add delete query

    //TODO: Add clear query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertElection(vararg elections: Election)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFollowing(vararg following: Following)

    @Query("select * from election_table")
    fun getAllElection(): List<Election>

    @Transaction
    @Query("select * from election_table")
    fun getElectionByFollowing(): List<ElectionByFollowing>

    @Transaction
    @Query("select * from following_table where electionId = :id")
    fun getFollowingByElectionId(id: Int): Following?

    @Update
    fun editElection(election: Election): Int

    @Update
    fun editFollowing(following: Following): Int

    @Query("select * from election_table where id = :electionId")
    fun getElectionById(electionId: Int): Election?

    @Query("delete from election_table where id = :electionId")
    fun deleteElectionById(electionId: Int): Int

    @Query("delete from election_table")
    fun deleteAllElection(): Int

    @Query("delete from following_table where electionId = :electionId")
    fun deleteFollowingId(electionId: Int): Int

    @Query("delete from following_table")
    fun deleteFollowing(): Int

}