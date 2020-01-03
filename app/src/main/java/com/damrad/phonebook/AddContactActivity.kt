package com.damrad.phonebook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_contact.*
import kotlinx.android.synthetic.main.content_add_contact.*


class AddContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        setSupportActionBar(toolbar)

        val intent = intent

        var editing = false

        var id: Int = -1

        if (intent.extras != null && intent.extras?.size() != 0 && intent.extras?.size()!! >= 5) {
            id = intent.getIntExtra(ShowContactActivity.EXTRA_ID, -1)
            val name = intent.getStringExtra(ShowContactActivity.EXTRA_NAME)
            val surname = intent.getStringExtra(ShowContactActivity.EXTRA_SURNAME)
            val email = intent.getStringExtra(ShowContactActivity.EXTRA_EMAIL)
            val phoneNr = intent.getIntExtra(ShowContactActivity.EXTRA_PHONE_NUMBER, -1)
            val gender = intent.getStringExtra(ShowContactActivity.EXTRA_GENDER)
            editing = intent.getBooleanExtra(ShowContactActivity.EXTRA_EDIT, false)

            if (id > -1 && phoneNr > -1) {
                addTitleTV.text = name
                addNameTV.text.insert(0, name)
                addSurnameTV.text.insert(0, surname)
                addEmailTV.text.insert(0, email)
                addPhoneTV.text.insert(0, phoneNr.toString())

                if (gender == getString(R.string.Woman)) {
                    chipWoman.isChecked = true
                } else {
                    chipMan.isChecked = true
                }
            } else {
                Toast.makeText(this, getString(R.string.loading_error), Toast.LENGTH_LONG).show()
                finish()
            }
        }

        cancelAddContact.setOnClickListener { finish() }

        saveAddContact.setOnClickListener {
            if (addNameTV.text.trim().isEmpty()) {
                addNameTV.error = getString(R.string.cannot_be_empty)
            } else if (addPhoneTV.text.trim().isEmpty()) {
                addPhoneTV.error = getString(R.string.cannot_be_empty)
            } else if (chipWoman.isChecked && chipMan.isChecked || !chipWoman.isChecked && !chipMan.isChecked) {
                Snackbar.make(AddContactLayout, getString(R.string.choice_only_one), Snackbar.LENGTH_LONG).show()
                chipMan.error = getString(R.string.choice_only_one)
                chipWoman.error = getString(R.string.choice_only_one)
            } else {

                val contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
                var choicedGender = ""

                if (chipWoman.isChecked) {
                    choicedGender = getString(R.string.Woman)
                } else if (chipMan.isChecked) {
                    choicedGender = getString(R.string.Man)
                }

                if (editing && id > -1) {
                    contactViewModel.deleteContactWithId(id)
                }

                contactViewModel.insert(
                    Contact(
                        addNameTV.text.toString().trim(),
                        addSurnameTV.text.toString().trim(),
                        addPhoneTV.text.toString().trim().toInt(),
                        addEmailTV.text.toString().trim(),
                        choicedGender.trim()
                    )
                )
                Toast.makeText(this, getString(R.string.contact_added), Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}
