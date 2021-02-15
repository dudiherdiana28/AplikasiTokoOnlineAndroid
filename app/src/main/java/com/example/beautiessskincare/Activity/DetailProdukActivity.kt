package com.example.beautiessskincare.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.beautiessskincare.Helper.Helper
import com.example.beautiessskincare.R
import com.example.beautiessskincare.model.Produk
import com.example.beautiessskincare.room.myDatabase
import com.example.beautiessskincare.util.Config
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_produk.*
import kotlinx.android.synthetic.main.item_produk.tv_harga
import kotlinx.android.synthetic.main.item_produk.tv_nama
import kotlinx.android.synthetic.main.toolbar_custom.*

class DetailProdukActivity : AppCompatActivity() {

    lateinit var myDb:myDatabase
    lateinit var produk:Produk
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)

        myDb = myDatabase.getInstance(this)!! // call database

        getInfo()
        mainButton()
        checkKeranjang()
    }

    private fun mainButton(){
        btn_keranjang.setOnClickListener(){
            val data = myDb.daoKeranjang().getProduk(produk.id)
            if (data == null) {
                insert()
            } else {
                data.jumlah += 1
                update(data)
            }
        }
        btn_favorit.setOnClickListener(){

            val listData = myDb.daoKeranjang().getAll() // get All data
            for(note :Produk in listData){
                println("-----------------------")
                println(note.name)
                println(note.harga)
            }
        }

        btn_toKeranjang.setOnClickListener(){
            val intent = Intent("event:keranjang")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            onBackPressed()
        }
    }


    private fun insert(){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().insert(produk) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
//                Log.d("respons", "data inserted")
//                DetailProdukActivity.make(findViewById(R.id.pesan),"Updated successfully",Snackbar.LENGTH_INDEFINITE,null,
//                        R.drawable.ic_baseline_shopping_cart_24,null, ContextCompat.getColor(this, R.color.biru))?.show()
                Snackbar.make( findViewById(R.id.pesan), "Berhasil menambahkan ke dalam keranjang", Snackbar.LENGTH_SHORT).show()
//                Toast.makeText(this, "Berhasil menambahkan ke dalam keranjang", Toast.LENGTH_SHORT).show()
            })
    }

    private fun update(data: Produk) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Snackbar.make( findViewById(R.id.pesan), "Berhasil menambahkan ke dalam keranjang", Snackbar.LENGTH_SHORT).show()
            })
    }

    private fun checkKeranjang(){
        var DataKeranjang = myDb.daoKeranjang().getAll()

        if (myDb.daoKeranjang().getAll().isNotEmpty()){
            div_angka.visibility = View.VISIBLE
//            tv_angka.text = ""+DataKeranjang.size
            tv_angka.text = DataKeranjang.size.toString()
        }else{
            div_angka.visibility = View.GONE
        }
    }

    private fun getInfo(){
        val data = intent.getStringExtra("extra")
        produk = Gson().fromJson<Produk>(data, Produk::class.java)

        tv_nama.text = produk.name
        tv_harga.text = Helper().gantiRupiah(produk.harga)
        tv_deskripsi.text = produk.deskripsi

        val img = Config.productUrl+produk.image
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.sweetchoco)
            .error(R.drawable.ladyglam)
            .resize(400, 400)
            .into(image)

        ///set Toolbar
        Helper().setToolbar(this,toolbar,produk.name)
//        setSupportActionBar(toolbar)
//        supportActionBar!!.title = produk.name
//        supportActionBar!!.setDisplayShowHomeEnabled(true)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}