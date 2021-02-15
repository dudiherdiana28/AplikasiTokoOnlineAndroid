package com.example.beautiessskincare.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.beautiessskincare.MainActivity
import com.example.beautiessskincare.R
import com.example.beautiessskincare.adapter.AdapterProduk
import com.example.beautiessskincare.adapter.AdapterSlider
import com.example.beautiessskincare.app.ApiConfig
import com.example.beautiessskincare.model.Produk
import com.example.beautiessskincare.model.ResponModel
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    lateinit var vpSlider: ViewPager
    lateinit var rvProduk: RecyclerView
    lateinit var rvProdukTerlasir: RecyclerView
    lateinit var rv_elektronik: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        getProduk()

        return view
    }

    fun displayProduk(){
        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.baner2)
        arrSlider.add(R.drawable.banner1)
        arrSlider.add(R.drawable.banner3)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManagerElektronik = LinearLayoutManager(activity)
        layoutManagerElektronik.orientation = LinearLayoutManager.HORIZONTAL

        rvProduk.adapter = AdapterProduk(requireActivity(), listProduk)
        rvProduk.layoutManager = layoutManager

        rvProdukTerlasir.adapter = AdapterProduk(requireActivity() , listProduk)
        rvProdukTerlasir.layoutManager = layoutManager2

        rv_elektronik.adapter = AdapterProduk(requireActivity(), listProduk)
        rv_elektronik.layoutManager = layoutManagerElektronik
    }

    private var listProduk:ArrayList<Produk> = ArrayList()
    fun getProduk(){
        ApiConfig.instanceRetrofit.getProduk().enqueue(object : Callback<ResponModel> {

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
//                pb.visibility = View.GONE
//                Toast.makeText(this@LoginActivity, "Error"+t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1){
                    listProduk = res.produks
                    displayProduk()
                }
            }
        })
    }

    fun init(view: View){
        vpSlider = view.findViewById(R.id.vp_slider)
        rvProduk = view.findViewById(R.id.rv_produk)
        rvProdukTerlasir = view.findViewById(R.id.rv_produkTerlasir)
        rv_elektronik = view.findViewById(R.id.rv_elektronik)
    }

//    val arrProduk: ArrayList<Produk>get(){
//        val arr = ArrayList<Produk>()
//        val p1 = Produk()
//        p1.nama = "HP 14_bs749tu"
//        p1.harga = "Rp.5.500.000"
//        p1.gambar = R.drawable.exposednude
//
//        val p2 = Produk()
//        p2.nama = "Hp Envy_13_aq0019tx"
//        p2.harga = "Rp.15.980.000"
//        p2.gambar = R.drawable.orangeshoot
//
//        val p3 = Produk()
//        p3.nama = "HP pavilion_13_an0006na"
//        p3.harga = "Rp.14.200.000"
//        p3.gambar = R.drawable.rubyheart
//
//        val p4 = Produk()
//        p4.nama = "Hp Envy_13_aq0019tx"
//        p4.harga = "Rp.15.980.000"
//        p4.gambar = R.drawable.sweetchoco
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//        arr.add(p4)
//
//        return arr
//    }
//    val arrProdukTerlaris: ArrayList<Produk>get(){
//        val arr = ArrayList<Produk>()
//        val p1 = Produk()
//        p1.nama = "HP 14_bs749tu"
//        p1.harga = "Rp.5.500.000"
//        p1.gambar = R.drawable.sweetchoco
//
//        val p2 = Produk()
//        p2.nama = "Hp Envy_13_aq0019tx"
//        p2.harga = "Rp.15.980.000"
//        p2.gambar = R.drawable.rubyheart
//
//        val p3 = Produk()
//        p3.nama = "HP pavilion_13_an0006na"
//        p3.harga = "Rp.14.200.000"
//        p3.gambar = R.drawable.orangeshoot
//
//        val p4 = Produk()
//        p4.nama = "Hp Envy_13_aq0019tx"
//        p4.harga = "Rp.15.980.000"
//        p4.gambar = R.drawable.exposednude
//
//        arr.add(p4)
//        arr.add(p1)
//        arr.add(p3)
//        arr.add(p2)
//
//        return arr
//    }
//    val arrElektronik: ArrayList<Produk>get(){
//        val arr = ArrayList<Produk>()
//        val p1 = Produk()
//        p1.nama = "HP 14_bs749tu"
//        p1.harga = "Rp.5.500.000"
//        p1.gambar = R.drawable.truebrown
//
//        val p2 = Produk()
//        p2.nama = "Hp Envy_13_aq0019tx"
//        p2.harga = "Rp.15.980.000"
//        p2.gambar = R.drawable.sweetchoco
//
//        val p3 = Produk()
//        p3.nama = "HP pavilion_13_an0006na"
//        p3.harga = "Rp.14.200.000"
//        p3.gambar = R.drawable.orangeshoot
//
//        val p4 = Produk()
//        p4.nama = "Hp Envy_13_aq0019tx"
//        p4.harga = "Rp.15.980.000"
//        p4.gambar = R.drawable.rubyheart
//
//        arr.add(p2)
//        arr.add(p3)
//        arr.add(p1)
//        arr.add(p4)
//
//        return arr
//    }

}