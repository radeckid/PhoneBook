package com.damrad.phonebook

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_show_contact.*
import kotlinx.android.synthetic.main.content_show_contact.*


class ShowContactActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "com.damrad.phonebook.id"
        const val EXTRA_NAME = "com.damrad.phonebook.name"
        const val EXTRA_SURNAME = "com.damrad.phonebook.surname"
        const val EXTRA_EMAIL = "com.damrad.phonebook.email"
        const val EXTRA_PHONE_NUMBER = "com.damrad.phonebook.phonenumber"
        const val EXTRA_GENDER = "com.damrad.phonebook.gender"
    }

    private val CALL_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_contact)
        setSupportActionBar(toolbar)

        val intent = intent

        if (intent.extras?.size() != 0 && intent.extras?.size()!! >= 5) {
            val id = intent.getIntExtra(EXTRA_ID, -1)
            val name = intent.getStringExtra(EXTRA_NAME)
            val surname = intent.getStringExtra(EXTRA_SURNAME)
            val email = intent.getStringExtra(EXTRA_EMAIL)
            val phoneNr = intent.getIntExtra(EXTRA_PHONE_NUMBER, -1)
            val gender = intent.getStringExtra(EXTRA_GENDER)

            if (id > -1 && phoneNr > -1) {
                toolbar.title = name
                nameTV.text = name
                surnameTV.text = surname
                emailTV.text = email
                phoneNrTV.text = phoneNr.toString()
                genderTV.text = gender
            } else {
                Toast.makeText(this, getString(R.string.loading_error), Toast.LENGTH_LONG).show()
                finish()
            }
        }

        editFab.setOnClickListener { view ->
            Snackbar.make(view, "Edytuj kontakt ", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        callContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL)
            val phoneCall: String = "tel:" + phoneNrTV.text
            intent.data = Uri.parse(phoneCall)

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.premissions_to_call), Toast.LENGTH_LONG).show()
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PERMISSION_REQUEST_CODE)
            } else {
                startActivity(intent)
            }
        }
    }
}
