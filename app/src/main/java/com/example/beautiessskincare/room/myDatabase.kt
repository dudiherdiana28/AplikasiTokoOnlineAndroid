package com.example.beautiessskincare.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.beautiessskincare.model.Alamat
import com.example.beautiessskincare.model.Produk

@Database(entities = [Produk::class, Alamat::class] /* List model Ex:NoteModel */, version = 1)
abstract class myDatabase : RoomDatabase() {
    abstract fun daoKeranjang(): DaoKeranjang // DaoNote
    abstract fun daoAlamat(): DaoAlamat // DaoNote

    companion object {
        private var INSTANCE: myDatabase? = null

        fun getInstance(context: Context): myDatabase? {
            if (INSTANCE == null) {
                synchronized(myDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        myDatabase::class.java, "MyDatabase221" // Database Name
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}