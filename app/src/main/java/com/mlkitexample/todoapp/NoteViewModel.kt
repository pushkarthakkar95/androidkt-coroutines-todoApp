package com.mlkitexample.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*

class NoteViewModel(val noteDao: NoteDao, application: Application): AndroidViewModel(application) {
    var listOfNoteLiveData = noteDao.getAllNotes()

    var viewModelJob = Job()

    var uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun addNote(note: Note){
        uiScope.launch { noteDao.insertNote(note) }
    }

    fun deleteNoteAtPosition(notePosition: Int){
        uiScope.launch { deleteNote(listOfNoteLiveData.value?.get(notePosition)!!) }
    }

    private fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }


}