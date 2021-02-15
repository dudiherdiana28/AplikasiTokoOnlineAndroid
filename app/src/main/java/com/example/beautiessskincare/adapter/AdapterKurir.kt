package com.example.beautiessskincare.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
import com.example.beautiessskincare.model.Rajaongkir.Costs
import com.example.beautiessskincare.model.Rajaongkir.Result
import com.example.beautiessskincare.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterKurir(var data: ArrayList<Costs>, var kurir:String, var listener: Listeners) : RecyclerView.Adapter<AdapterKurir.Holder> () {
    class Holder (view: View) :RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvLamaPengiriman = view.findViewById<TextView>(R.id.tv_lamapengiriman)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val tvBerat = view.findViewById<TextView>(R.id.tv_berat)
        val layout = view.findViewById<LinearLayout>(R.id.layout_kurir)
        val rb = view.findViewById<RadioButton>(R.id.rb)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_kurir, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]

//        holder.rd_alamat.isChecked = a.isSelected
        holder.tvNama.text = kurir + " " +a.service
        val cost = a.cost[0]
        holder.tvLamaPengiriman.text = cost.etd + " hari kerja"
        holder.tvHarga.text = Helper().gantiRupiah(cost.value)
        holder.tvBerat.text = "1 kg x" + Helper().gantiRupiah(cost.value)
 //        holder.tvAlamat.text = a.alamat + ", "+ a.kota + ", "+ a.kecamatan + ", "+ a.kodepos +", ("+ a.type +")"
//
//        holder.rd_alamat.setOnClickListener {
//            a.isSelected = true
//            listener.onClicked(a)
//        }
//
//        holder.layout_alamat.setOnClickListener {
//
//            a.isSelected = true
//            listener.onClicked(a)
//        }
    }

    interface Listeners {
        fun onClicked(data: Alamat)
    }
}