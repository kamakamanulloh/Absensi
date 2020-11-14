package com.softwarepintarcom.absensi

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softwarepintarcom.absensi.API.Utils.id
import com.softwarepintarcom.absensi.API.Utils.mac
import com.softwarepintarcom.absensi.API.Utils.nama
import com.softwarepintarcom.absensi.API.Utils.session
import com.softwarepintarcom.absensi.loginpgw.pgwPresenter
import com.softwarepintarcom.absensi.loginpgw.pgwView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.net.NetworkInterface
import java.util.*
import kotlin.experimental.and



class MainActivity : AppCompatActivity(),pgwView {

    private val PREF_NAME = "my_shared_preferences"
    lateinit var pgwPresenter: pgwPresenter


    var session_status="session_status"

    private lateinit var progressDialog : ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val wInfo = wifiManager.connectionInfo
        val macAddress = wInfo.macAddress
        et_pswd.setText(getMacAddr())
        pgwPresenter= pgwPresenter((this))
        progressDialog = ProgressDialog(this)

        btnlogin.onClick {

            when {
                et_nip.text.isEmpty() -> {
                    et_nip.error = "Kolom Harus Diisi"
                }
                et_pswd.text.isEmpty() -> {
                    et_pswd.error = "Kolom Harus Diisi"
                }
                else -> {
                    progressDialog.setMessage("Proses Mohon Tunggu")
                    progressDialog.show()
                    pgwPresenter.getPgw(et_nip.text.toString(), et_pswd.text.toString())
                }
            }
        }

        val sharedPref=getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)


        session=sharedPref.getBoolean(session_status,false)
        id=sharedPref.getString("id",null)
        nama=sharedPref.getString("nama",null)
        mac=sharedPref.getString("mac",null)

        if (session){
            val intent=Intent(this,HomeActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("nama",nama)
            intent.putExtra("mac",mac)

            startActivity(intent)
            finish()




        }

    }

    fun getDevice(){
//        val manufacture= Build.MANUFACTURER
//        val model = Build.MODEL
//        if (model.startsWith(manufacture)){
//            return capitalize(model)
//        }
//        else{
//            return capitalize(manufacture) +" "+model
//        }
    }



    fun getMacAddr(): String? {
        try {
            val all: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (!nif.name.equals("wlan0", ignoreCase = true)) continue
                val macBytes = nif.hardwareAddress ?: return ""
                val res1 = StringBuilder()
                for (b in macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.isNotEmpty()) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (ex: Exception) {
        }
        return "02:00:00:00:00:00"
    }

    override fun onSuccesPgw(pesan: String, nip: String, nama: String, status: String, id: String) {
        progressDialog = ProgressDialog(this)
        progressDialog.dismiss()
        alert {
            message="Proses Berhasil"
        }.show()
        val sharedPref=getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)

        val editor=sharedPref.edit()
        editor.putBoolean(session_status, true)
        editor.putString("id",id)
        editor.putString("nip",nip)
        editor.putString("nama",nama)
        editor.putString("mac",et_pswd.text.toString())
        editor.commit()
        val intent=Intent(this,HomeActivity::class.java)
        intent.putExtra("id",id)
        intent.putExtra("nip",nip)
        intent.putExtra("nama",nama)
        intent.putExtra("mac",et_pswd.text.toString())
        startActivity(intent)
        finish()

    }

    override fun onFailedPgw(pesan: String) {
        progressDialog = ProgressDialog(this)
        progressDialog.dismiss()
       alert {
           message=pesan
       }.show()
    }
}