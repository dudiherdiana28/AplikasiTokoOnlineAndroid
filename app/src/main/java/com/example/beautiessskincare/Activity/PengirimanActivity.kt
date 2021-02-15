package com.example.beautiessskincare.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beautiessskincare.Helper.Helper
import com.example.beautiessskincare.R
import com.example.beautiessskincare.adapter.AdapterKurir
import com.example.beautiessskincare.app.ApiConfigAlamat
import com.example.beautiessskincare.model.Alamat
import com.example.beautiessskincare.model.Rajaongkir.Costs
import com.example.beautiessskincare.model.Rajaongkir.ResponOngkir
import com.example.beautiessskincare.model.Rajaongkir.Result
import com.example.beautiessskincare.room.myDatabase
import com.example.beautiessskincare.util.ApiKey
import kotlinx.android.synthetic.main.activity_pengiriman.*
import kotlinx.android.synthetic.main.activity_tambah_alamat.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengirimanActivity : AppCompatActivity() {

    lateinit var  myDb : myDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengiriman)
        Helper().setToolbar(this,toolbar,"Pengiriman")

        myDb = myDatabase.getInstance(this)!!
        mainButton()
        setSpinner()
    }

    fun setSpinner(){
        val arryString =ArrayList<String>()
        arryString.add("JNE")
        arryString.add("POS")
        arryString.add("TIKI")

        val adapter =  ArrayAdapter<Any>(this,R.layout.item_spinner,arryString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_pengiriman.adapter = adapter
        spn_pengiriman.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long, ) {
                if (position !=0){
                    getOngkir(spn_pengiriman.selectedItem.toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    @SuppressLint("SetTextI18n")
    fun checkAlamat(){

        if (myDb.daoAlamat().getByStatus(true) != null){
            div_alamat.visibility = View.VISIBLE
            div_kosong_p.visibility = View.GONE
            div_metodePengiriman.visibility = View.VISIBLE


            val a = myDb.daoAlamat().getByStatus(true)!!
            tv_nama.text = a.name
            tv_phone.text = a.phone
            tv_alamat.text = a.alamat + ", "+ a.kota + ", "+ a.kecamatan + ", "+ a.kodepos +", ("+ a.type +")"
            btn_tambahAlamat.text = "Ubah Alamat"

            getOngkir("JNE")
        }else{
            div_alamat.visibility = View.GONE
            div_kosong_p.visibility = View.VISIBLE
            btn_tambahAlamat.text = "Tambah Alamat"
        }
    }

    private fun mainButton(){
        btn_tambahAlamat.setOnClickListener {
            startActivity(Intent(this, ListAlamatActivity::class.java))
        }
    }

    private fun getOngkir(kurir:String){
        val alamat = myDb.daoAlamat().getByStatus(true)

        val origin = "501"
        val destination = "" + alamat!!.id_kota.toString()
        val berat = 1000

        ApiConfigAlamat.instanceRetrofit.getOngkir(ApiKey.key, origin, destination,berat,kurir.toLowerCase()).enqueue(object : Callback<ResponOngkir> {
            override fun onResponse(call: Call<ResponOngkir>, response: Response<ResponOngkir>) {
                if (response.isSuccessful){
                    Log.d("Success","Berhasil:" + response.message())

                    val result = response.body()!!.rajaongkir.results
                    if (result.isNotEmpty()){
                        displayOngkir(result[0].code.toUpperCase(), result[0].costs)
                    }

                }else{
                    Log.d("Error","Gagal memuat data:" + response.message())
                }
            }

            override fun onFailure(call: Call<ResponOngkir>, t: Throwable) {
                Log.d("Error","Gagal memuat data:" + t.message)
            }

        })
    }

    private fun displayOngkir(kurir : String, arrayList : ArrayList<Costs>) {

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_metode.adapter = AdapterKurir(arrayList, kurir, object : AdapterKurir.Listeners {
            override fun onClicked(data: Alamat) {
            }
        })
        rv_metode.layoutManager = layoutManager
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        checkAlamat()
        super.onResume()
    }
}