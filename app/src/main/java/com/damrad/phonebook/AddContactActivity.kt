package com.damrad.phonebook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_contact.*
import kotlinx.android.synthetic.main.content_add_contact.*
import kotlinx.android.synthetic.main.content_show_contact.*

class AddContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        setSupportActionBar(toolbar)


        chipWoman.setOnClickListener {
            if (chipWoman.isChecked) {
                Toast.makeText(this, "Kobieta", Toast.LENGTH_SHORT).show()
            }
        }

        chipMan.setOnClickListener {
            if (chipMan.isChecked) {
                Toast.makeText(this, "Mężczynza", Toast.LENGTH_SHORT).show()
            }
        }

        cancelAddContact.setOnClickListener { finish() }

        saveAddContact.setOnClickListener {
            if (addNameTV.text.isEmpty()) {
                addNameTV.error = getString(R.string.cannot_be_empty)
            } else if (chipWoman.isChecked && chipMan.isChecked || !chipWoman.isChecked && !chipMan.isChecked) {
                Snackbar.make(AddContactLayout, getString(R.string.choice_only_one), Snackbar.LENGTH_LONG).show()
            } else {

                val contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
                var choicedGender = ""

                if (chipWoman.isChecked) {
                    choicedGender = getString(R.string.Woman)
                } else if (chipMan.isChecked) {
                    choicedGender = getString(R.string.Man)
                }

                //TODO sprawdzać czy nie są puste pola
                contactViewModel.insert(
                    Contact(
                        addNameTV.text.toString(),
                        addSurnameTV.text.toString(),
                        addPhoneTV.text.toString().toInt(),
                        addEmailTV.text.toString(),
                        choicedGender
                    )
                )

                finish()
            }
        }
    }
}
