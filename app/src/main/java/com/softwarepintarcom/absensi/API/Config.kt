package com.softwarepintarcom.absensi.API

import com.softwarepintarcom.absensi.loginpgw.PgwResponse
import com.softwarepintarcom.absensi.scan.AbsensiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

object Config {
    fun getInceptor():OkHttpClient{
        val logging=HttpLoggingInterceptor()
        logging.level=HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    }
    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://absensi.softwarepintar.com/api_absensi/")
            .client(getInceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getService()= getRetrofit().create(ApiService::class.java)

    interface ApiService{
        @FormUrlEncoded
        @POST("index.php/LoginApi/loginpgw")
        fun loginPgw(
            @Field("nip")nip:String,
            @Field("mac")mas:String
        ):Call<PgwResponse>

        @FormUrlEncoded
        @POST("index.php/CekScan/alamat")
        fun getAlamat(
            @Field("kode")kode:String,
            @Field("mac")mac:String,
            @Field("idpeg")pgwid:String,
            @Field("status")stratus:String
        ):Call<AbsensiResponse>



    }


}