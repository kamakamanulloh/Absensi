package com.softwarepintarcom.absensi.scan.datang

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.*
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.softwarepintarcom.absensi.API.Utils.id
import com.softwarepintarcom.absensi.API.Utils.mac
import com.softwarepintarcom.absensi.HomeActivity
import com.softwarepintarcom.absensi.R
import com.softwarepintarcom.absensi.scan.AbsenView
import com.softwarepintarcom.absensi.scan.AbsensiPresenter
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.intentFor

@Suppress("UNREACHABLE_CODE")
class DatangActivity : AppCompatActivity(),AbsenView {
    lateinit var absensiPresenter: AbsensiPresenter


    private lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datang)
        absensiPresenter= AbsensiPresenter(this)

        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)

        codeScanner = CodeScanner(this, scannerView)
        checkCameraPermission()

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
//                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
                absensiPresenter.absensi(it.toString(),mac.toString(),id.toString(),"D")
            }
//            showAlertDialog()

        }

        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(
                    this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }


    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setCancelable(true)
        builder.setPositiveButton(
            "SCAN LAGI"
        ) { dialog, id ->
            dialog.cancel()
            codeScanner.startPreview()
        }
        builder.setNegativeButton(
            "CANCEL"
        ) { dialog, id -> dialog.cancel() }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun checkCameraPermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    codeScanner.startPreview()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {}
                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .check()
    }



    override fun onResume() {
        super.onResume()
        checkCameraPermission()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onSuccesAbsen(pesan: String) {
        alert {
            message=pesan
        }.show()

        startActivity(intentFor<HomeActivity>().newTask())
    }

    override fun onFailedAbsen(pesan: String) {
        alert {
            message=pesan
        }.show()
    }
}