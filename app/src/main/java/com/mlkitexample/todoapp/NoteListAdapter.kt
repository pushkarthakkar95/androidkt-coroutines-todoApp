package com.mlkitexample.todoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mlkitexample.todoapp.databinding.NoteItemBinding

class NoteListAdapter(var context: Context):
    RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {
    var listOfNotes : List<Note>? = null
    fun setData(newList: List<Note>){
        listOfNotes = newList
        notifyDataSetChanged()
    }
    class NoteViewHolder(var binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOfNotes?.size ?: 0
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = listOfNotes?.get(position)
        holder.binding.note = note
    }
}