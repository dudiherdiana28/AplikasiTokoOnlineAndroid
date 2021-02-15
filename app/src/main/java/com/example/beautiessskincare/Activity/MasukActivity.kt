package com.example.beautiessskincare.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.beautiessskincare.Helper.SharedPref
import com.example.beautiessskincare.R
import kotlinx.android.synthetic.main.activity_masuk.*

class MasukActivity : AppCompatActivity() {
    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masuk)

        s = SharedPref(this)

        mainButton()
    }

    private fun mainButton(){
        btn_prosesLogin.setOnClickListener {
            s.setStatusLogin(true)
            startActivity(Intent(this,LoginActivity::class.java))
        }

        btn_register.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}