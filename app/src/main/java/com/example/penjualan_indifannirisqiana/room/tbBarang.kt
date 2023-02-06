package com.example.penjualan_indifannirisqiana.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tbBarang (
    @PrimaryKey
    val id :Int,
    val nama :String,
    val harga :Int,
    val stok :Int
)