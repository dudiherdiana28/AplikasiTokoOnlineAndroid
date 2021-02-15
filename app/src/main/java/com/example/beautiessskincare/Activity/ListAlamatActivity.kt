package com.example.beautiessskincare.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beautiessskincare.Helper.Helper
import com.example.beautiessskincare.R
import com.example.beautiessskincare.adapter.AdapterAlamat
import com.example.beautiessskincare.model.Alamat
import com.example.beautiessskincare.room.myDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list_alamat.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class ListAlamatActivity : AppCompatActivity() {

    lateinit var myDb : myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alamat)
        Helper().setToolbar(this,toolbar,"Pilih Alamat")
        myDb = myDatabase.getInstance(this)!!

        mainButton()
    }

    private fun displayAlamat() {
        val arrayList = myDb.daoAlamat().getAll() as ArrayList

        if (arrayList.isEmpty()) div_kosong.visibility = View.VISIBLE
        else div_kosong.visibility = View.GONE

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_alamat.adapter = AdapterAlamat(arrayList, object : AdapterAlamat.Listeners {
            override fun onClicked(data: Alamat) {
                if (myDb.daoAlamat().getByStatus(true) != null){
                    val alamatActive = myDb.daoAlamat().getByStatus(true)!!
                    alamatActive.isSelected = false
                    updateActive(alamatActive, data)
                }
            }
        })
        rv_alamat.layoutManager = layoutManager
    }

    private fun updateActive(dataActive: Alamat, dataNonActive: Alamat) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoAlamat().update(dataActive) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                updateNonActive(dataNonActive)
            })
    }

    private fun updateNonActive(data: Alamat) {
        data.isSelected = true
        CompositeDisposable().add(Observable.fromCallable { myDb.daoAlamat().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                onBackPressed()
            })
    }

    override fun onResume() {
        displayAlamat()
        super.onResume()
    }

    private fun mainButton(){
        btn_tambahAlamat.setOnClickListener {
            startActivity(Intent(this,TambahAlamatActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}