package com.damrad.phonebook

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.damrad.phonebook.ShowContactActivity.Companion.EXTRA_EMAIL
import com.damrad.phonebook.ShowContactActivity.Companion.EXTRA_GENDER
import com.damrad.phonebook.ShowContactActivity.Companion.EXTRA_ID
import com.damrad.phonebook.ShowContactActivity.Companion.EXTRA_NAME
import com.damrad.phonebook.ShowContactActivity.Companion.EXTRA_PHONE_NUMBER
import com.damrad.phonebook.ShowContactActivity.Companion.EXTRA_SURNAME
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_show_contact.view.*
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

        adapter = ContactAdapter(this)
        mainRecycler.adapter = adapter
        mainRecycler.layoutManager = LinearLayoutManager(this)

        //Observe list change
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.allContacts.observe(this, Observer { contacts ->
            contacts?.let { adapter.setList(it as ArrayList<Contact>) }
        })

        fabAdd.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivity(intent)
        }

        onSwipeRecycler()
        onClickRecycler()
    }

    private fun onClickRecycler() {
        adapter.setOnItemClickListener(object : OnClickInterface {
            override fun setOnClick(position: Int) {
                val intent = Intent(applicationContext, ShowContactActivity::class.java)
                intent.putExtra(EXTRA_ID, adapter.getItemAt(position).id)
                intent.putExtra(EXTRA_NAME, adapter.getItemAt(position).name)
                intent.putExtra(EXTRA_SURNAME, adapter.getItemAt(position).surname)
                intent.putExtra(EXTRA_EMAIL, adapter.getItemAt(position).email)
                intent.putExtra(EXTRA_PHONE_NUMBER, adapter.getItemAt(position).phoneNumber)
                intent.putExtra(EXTRA_GENDER, adapter.getItemAt(position).gender)
                startActivity(intent)
            }
        })

    }

    private fun onSwipeRecycler() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }
        }).attachToRecyclerView(mainRecycler)
    }

}
