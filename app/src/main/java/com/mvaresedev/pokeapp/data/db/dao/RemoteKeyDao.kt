package com.mvaresedev.pokeapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvaresedev.pokeapp.data.db.entities.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: RemoteKeyEntity)

    @Query("SELECT * FROM remote_keys ORDER BY id ASC")
    suspend fun getAllRemoteKeys(): List<RemoteKeyEntity>

    @Query("DELETE FROM remote_keys")
    suspend fun clearAllKeys()
}