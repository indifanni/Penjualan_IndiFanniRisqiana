package com.example.penjualan_indifannirisqiana.room

import androidx.room.*

@Dao
interface DaoBrg {
    @Insert
    fun addBarang (barang: tbBarang)

    @Update
    fun updateBarang (barang: tbBarang)

    @Delete
    fun deleteBarang (barang: tbBarang)

    @Query("SELECT * FROM tbBarang")
    fun tampilBarang(): List<tbBarang>

    @Query("SELECT * FROM tbBarang WHERE id=:brng_id")
    suspend fun tampil(brng_id: Int): List<tbBarang>

}