package com.faizalputraramadhan.post4_199

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BarangDao {

    @Query("DELETE FROM barang")
    fun deleteAll()


    @Query("SELECT MAX(id) FROM barang")
    fun getLastId(): Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(barang: Barang)

    @Update
    fun update(barang: Barang)

    @Delete
    fun delete(barang: Barang)

    @Query("SELECT * from barang ORDER BY id ASC")
    fun getAllBarang(): LiveData<List<Barang>>

    @Query("SELECT * FROM barang WHERE id = :barangId")
    fun getBarangById(barangId: Int): Barang
}