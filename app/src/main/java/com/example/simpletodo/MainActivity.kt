package com.example.simpletodo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val OnLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. Remove the item from the list
                listOfTasks.removeAt(position)
                // 2. Notify teh adapter that our data has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        /*findViewById<Button>(R.id.button).setOnClickListener {
            Log.i("Elemento", "User clicked on button")

        }*/

        loadItems()


        //Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, OnLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // set up the button and input field, so that the user can enter a task and add it to the list

        val inputTextField = findViewById<EditText>(R.id.addtaskfield)

        //Get a reference to the button and then set an onClickListener
        findViewById<Button>(R.id.button).setOnClickListener {

            //1. Grab the text the user has inputted into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // 2. Add the string to out list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // 3. Reset text field
            inputTextField.setText("")

            saveItems()
        }
    }

            // Save the data that the user had inputted
            // Save data by writing and reading from a file

            // Get the file we need
            fun getDataFile(): File {
                // Every line is going to represent a specific task
                return File(filesDir, "data.txt")
            }

            //Load the item by reading every line in the data file
            fun loadItems(){
                try{
                    listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
                } catch(ioException: IOException){
                    ioException.printStackTrace()
                }
            }

            // Save item by writing them into our data file
            fun saveItems() {
                try {
                    FileUtils.writeLines(getDataFile(), listOfTasks)
                } catch (ioException: IOException) {
                    ioException.printStackTrace()
                }
            }


    }