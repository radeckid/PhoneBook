package com.damrad.phonebook

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        private const val newContactActivityRequestCode = 1
    }

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        val recyclerView = findViewById<RecyclerView>(R.id.mainRecycler)
        adapter = ContactAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        contactViewModel.allContacts.observe(this, Observer { contacts ->
            contacts?.let { adapter.setList(it as ArrayList<Contact>) }
        })

        fab.setOnClickListener {
            //            val random = Random()
//            contactViewModel.insert(Contact("${random.nextInt()}Damian", "Radecki", 533252518, ""))
            val intent = Intent(this, AddContact_Activity::class.java)
            startActivity(intent)
        }
    }


}
