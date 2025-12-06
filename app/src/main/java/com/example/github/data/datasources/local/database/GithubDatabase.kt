package com.example.github.data.datasources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.github.data.datasources.local.dao.BranchDao

/**
 * Created by AsmaaHassan on 05,December,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */
// why i'm using () -> ask chatgpt
// search the annotation design patter
//search exportschema



// you should typically have only one main abstract RoomDatabase
// which serves as a singleton.
// This class is always an abstract class because Room generates the concrete implementation for you at compile time.
// List all entities and set the version number
@Database(entities = [], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase(){
    // Expose all Data Access Objects (DAOs) here
    abstract fun branchDao(): BranchDao
}