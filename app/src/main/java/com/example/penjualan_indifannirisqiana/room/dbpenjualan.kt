package com.example.penjualan_indifannirisqiana.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [tbBarang::class],
    version = 1
)
abstract class dbpenjualan : RoomDatabase(){
    abstract fun DaoBarang() : DaoBrg

    companion object {
        @Volatile private var instance : dbpenjualan? = null

        private val LOCK = Any()
        operator fun  invoke(context: Context)= instance?: synchronized(LOCK){
            instance?: buildDataBase (context).also{
                instance= it
            }
        }
        private fun buildDataBase(context: Context)= Room.databaseBuilder(
            context.applicationContext,dbpenjualan::class.java,"barang.db"
        ).build()
    }
}