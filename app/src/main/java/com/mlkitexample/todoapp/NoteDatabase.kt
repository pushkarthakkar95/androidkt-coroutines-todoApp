package com.mlkitexample.todoapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao
    companion object{
        @Volatile
        var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context):NoteDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(context.applicationContext,
                                                    NoteDatabase::class.java,
                                                    "note_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}