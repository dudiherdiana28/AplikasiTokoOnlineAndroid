package com.example.beautiessskincare.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.beautiessskincare.Activity.DetailProdukActivity
import com.example.beautiessskincare.Activity.PengirimanActivity
import com.example.beautiessskincare.Helper.Helper
import com.example.beautiessskincare.R
import com.example.beautiessskincare.model.Alamat
import com.example.beautiessskincare.model.Produk
import com.example.beautiessskincare.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterAlamat(var data: ArrayList<Alamat>, var listener: Listeners) : RecyclerView.Adapter<AdapterAlamat.Holder> () {
    class Holder (view: View) :RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvPhone = view.findViewById<TextView>(R.id.tv_phone)
        val tvAlamat = view.findViewById<TextView>(R.id.tv_alamat)
        val layout_alamat = view.findViewById<CardView>(R.id.layout_alamat)
        val rd_alamat = view.findViewById<RadioButton>(R.id.rd_alamat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_alamat, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]

        holder.rd_alamat.isChecked = a.isSelected
        holder.tvNama.text = a.name
        holder.tvPhone.text = a.phone
        holder.tvAlamat.text = a.alamat + ", "+ a.kota + ", "+ a.kecamatan + ", "+ a.kodepos +", ("+ a.type +")"

        holder.rd_alamat.setOnClickListener {
            a.isSelected = true
            listener.onClicked(a)
        }

        holder.layout_alamat.setOnClickListener {

            a.isSelected = true
            listener.onClicked(a)
        }
    }

    interface Listeners {
        fun onClicked(data: Alamat)
    }
}