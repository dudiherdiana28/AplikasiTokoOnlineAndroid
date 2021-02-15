package com.example.beautiessskincare.app

import com.example.beautiessskincare.model.Rajaongkir.ResponOngkir
import com.example.beautiessskincare.model.ResponModel
import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") nomertlp: String,
        @Field("password") password: String
    ):Call<ResponModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
            @Field("email") email: String,
            @Field("password") password: String
    ):Call<ResponModel>

    @GET("produk")
    fun getProduk():Call<ResponModel>

    @GET("province")
    fun getProvinsi(
        @Header("key") key: String,
    ):Call<ResponModel>

    @GET("city")
    fun getKota(
        @Header("key") key: String,
        @Query("province") id:String
    ):Call<ResponModel>

    @GET("kecamatan")
    fun getKecamatan(
        @Query("id_kota") id:Int
    ):Call<ResponModel>

    @FormUrlEncoded
    @POST("cost")
    fun getOngkir(
        @Header("key") key: String,
        @Field("origin") origin: String,
        @Field("destination") destination: String,
        @Field("weight") weight: Int,
        @Field("courier") courier: String
    ):Call<ResponOngkir>
}