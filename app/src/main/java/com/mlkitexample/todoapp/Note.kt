package com.mlkitexample.todoapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Int = 0,

    var title: String,

    var description: String = "")