package com.mlkitexample.todoapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mlkitexample.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val application = requireNotNull(this).application

        val dataSource = NoteDatabase.getInstance(application).noteDao

        val viewModelFactory = NoteViewModelFactory(dataSource,application)

        var adapter = NoteListAdapter(this)
        setupRecyclerView(adapter)

        viewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        viewModel.listOfNoteLiveData.observe(this, Observer {
            if(it!=null){
                adapter.setData(it)
                binding.noToDoTV.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
            }
        })

        binding.btn.setOnClickListener {
            showNewNoteDialog()
        }


    }

    fun setupRecyclerView(adapter: NoteListAdapter){
        binding.noteRecyclerView.adapter = adapter
        binding.noteRecyclerView.layoutManager = LinearLayoutManager(this)
        val helper =  object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteNoteAtPosition(viewHolder.adapterPosition)
            }
        }
        ItemTouchHelper(helper).attachToRecyclerView(binding.noteRecyclerView)
    }

    fun showNewNoteDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("New To Do Note")
        val customLayout = layoutInflater.inflate(R.layout.to_do_form,null)
        builder.setView(customLayout)
        val titleView: EditText = customLayout.findViewById(R.id.titleFormET)
        val descriptionView: EditText = customLayout.findViewById(R.id.descriptionET)
        builder.setPositiveButton("Add Note"){
            dialog, which ->  if(titleView.text.toString().isNotEmpty()){
                viewModel.addNote(Note(title = titleView.text.toString(), description = descriptionView.text.toString()))
                dialog.dismiss()
            }
        }
        builder.setNegativeButton("Cancel"){
            dialog, which ->  dialog.dismiss()
        }
        builder.setCancelable(false)
        builder.show()
    }
}
