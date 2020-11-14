package com.softwarepintarcom.absensi.loginpgw

interface pgwView {
    fun onSuccesPgw(pesan:String,nip:String,nama:String,status:String,id:String)
    fun onFailedPgw(pesan: String)

}