package com.damrad.phonebook

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_show_contact.*
import java.util.*


class ContactAdapter internal constructor(context: Context) : RecyclerView.Adapter<ContactAdapter.ContactHolder?>() {
    private var onClickInterface: OnClickInterface? = null
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var list = emptyList<Contact>()

    class ContactHolder(itemView: View, onClickInterface: OnClickInterface?) : RecyclerView.ViewHolder(itemView) {

        val nameSurnameTV: TextView
        val avatarIMG: ImageView

        init {
            nameSurnameTV = itemView.findViewById(R.id.nameSurnameTV)
            avatarIMG = itemView.findViewById(R.id.avatarIMG)
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onClickInterface?.setOnClick(position)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val view: View = inflater.inflate(R.layout.item_row, parent, false)
        return ContactHolder(view, onClickInterface)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        if (list[position].gender.toLowerCase() == "kobieta") {
            holder.avatarIMG.setImageResource(R.drawable.woman)
        } else {
            holder.avatarIMG.setImageResource(R.drawable.man)
        }

        val name = list[position].name + " " + list[position].surname.trim()
        holder.nameSurnameTV.text = name
    }



    fun setOnItemClickListener(onClickInterface: OnClickInterface?) {
        this.onClickInterface = onClickInterface
    }

    fun getItemAt(position: Int): Contact {
        return list[position]
    }

    fun setList(list: ArrayList<Contact>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun getList(): List<Contact> {
        return list
    }

    override fun getItemCount(): Int {
        return list.size
    }
}