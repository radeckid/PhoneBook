package com.damrad.phonebook

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.avatarIMG.setImageResource(R.drawable.ic_launcher_background)
        holder.nameSurnameTV.text = list[position].name + " " + list[position].surname.trim()
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

    override fun getItemCount(): Int {
        return list.size
    }
}