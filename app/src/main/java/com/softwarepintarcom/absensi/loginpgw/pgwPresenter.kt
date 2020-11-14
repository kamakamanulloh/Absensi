package com.softwarepintarcom.absensi.loginpgw

import com.softwarepintarcom.absensi.API.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class pgwPresenter(val pgwView: pgwView) {
    fun getPgw(nip:String,mac:String){
        Config.getService()
            .loginPgw(nip,mac)
            .enqueue(object :Callback<PgwResponse>{
                override fun onResponse(call: Call<PgwResponse>, response: Response<PgwResponse>) {
                    if (response.isSuccessful){
                        if (response.body()?.status==202){
                            pgwView.onSuccesPgw(response.body()!!.pesan.toString(),
                                response.body()!!.nip.toString(), response.body()!!.nama.toString(),
                                response.body()!!.status.toString(), response.body()!!.id.toString()
                            )
                        }
                        else{
                            pgwView.onFailedPgw(response.body()!!.pesan.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<PgwResponse>, t: Throwable) {
                    pgwView.onFailedPgw("error")
                }

            })
    }

}