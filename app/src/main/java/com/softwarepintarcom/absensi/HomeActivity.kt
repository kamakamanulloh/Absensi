package com.softwarepintarcom.absensi

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.softwarepintarcom.absensi.API.Utils
import com.softwarepintarcom.absensi.scan.AbsenView
import com.softwarepintarcom.absensi.scan.AbsensiPresenter
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk27.coroutines.onClick


class HomeActivity : AppCompatActivity(), AbsenView {
    private var fab_open:Animation?=null
    private var fab_close:Animation?=null
    private var fab_clock:Animation?=null
    private var fab_anticlock:Animation?=null
    var isOpen:Boolean?=false
    lateinit var absensiPresenter: AbsensiPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        absensiPresenter= AbsensiPresenter(this)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        navView.setupWithNavController(navController)

        fab_close=AnimationUtils.loadAnimation(this,R.anim.fab_close)
        fab_anticlock=AnimationUtils.loadAnimation(this,R.anim.fab_rotate_anticlock)
        fab_clock=AnimationUtils.loadAnimation(this,R.anim.fab_rotate_clock)
        fab_open=AnimationUtils.loadAnimation(this,R.anim.fab_open)

        fab_main.onClick {
            if (isOpen!!){
                datang.startAnimation(fab_close)
                pulang.startAnimation(fab_close)
                datang.isClickable=false
                pulang.isClickable=false
                isOpen=false

            }
            else{
                datang.startAnimation(fab_open)
                pulang.startAnimation(fab_open)
                datang.isClickable=true
                pulang.isClickable=true
                isOpen=true

            }
        }
        datang.onClick {
//            startActivity(intentFor<DatangActivity>().newTask())
            absensiPresenter.absensi("123456", Utils.mac.toString(), Utils.id.toString(),"D")
        }
    }

    override fun onSuccesAbsen(pesan: String) {
        alert {
            message=pesan
        }.show()

    }

    override fun onFailedAbsen(pesan: String) {
        alert {
            message=pesan
        }.show()

    }
}