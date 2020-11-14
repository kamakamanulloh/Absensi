package com.softwarepintarcom.absensi.scan

import android.util.Log
import com.softwarepintarcom.absensi.API.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs

class AbsensiPresenter(val absenView: AbsenView) {
    fun absensi(kode:String,mac:String,idpeg:String,status:String){
        Config.getService()
            .getAlamat(kode,mac,idpeg,status)
            .enqueue(object :Callback<AbsensiResponse>{
                override fun onResponse(
                    call: Call<AbsensiResponse>,
                    response: Response<AbsensiResponse>
                ) {
                   if (response.isSuccessful){
                       val value=response.body()?.status
                       if (value==200){
                           absenView.onSuccesAbsen(response.body()!!.pesan!!)

                       }
                       else{
                           absenView.onFailedAbsen(response.body()?.pesan!!)
                       }
                   }

                }

                override fun onFailure(call: Call<AbsensiResponse>, t: Throwable) {
                    absenView.onFailedAbsen(t.localizedMessage)
                    Log.d("Error","Error Status")
                }

            })

    }
}