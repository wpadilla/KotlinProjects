package com.example.stores

import androidx.room.*

@Dao
interface StoreDao {
    @Query("SELECT * FROM StoreEntity")
    fun getAllStores(): MutableList<StoreEntity>

    @Query("SELECT * FROM StoreEntity WHERE id = :id")
    fun getStore(id: Long): StoreEntity

    @Insert
    fun addStore(store: StoreEntity): Long

    @Update
    fun updateStore(store: StoreEntity)

    @Delete
    fun deleteStore(store: StoreEntity)

}