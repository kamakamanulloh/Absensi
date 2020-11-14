package com.softwarepintarcom.absensi.scan

interface AbsenView {
    fun onSuccesAbsen(pesan:String)
    fun onFailedAbsen(pesan: String)
}