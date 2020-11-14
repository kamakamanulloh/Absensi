package com.softwarepintarcom.absensi.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.softwarepintarcom.absensi.MainActivity
import com.softwarepintarcom.absensi.R
import com.softwarepintarcom.absensi.scan.datang.DatangActivity
import kotlinx.android.synthetic.main.absensi_layout.*
import kotlinx.android.synthetic.main.absensi_layout.view.*

import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.newTask
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.intentFor

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)


        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnabsensi.onClick {
            menu()
        }
    }

    private fun menu() {
        val dialog = Dialog(requireContext())
        // Get the layout inflater
        val view = layoutInflater.inflate(R.layout.absensi_layout, null)
        dialog.setContentView(view)
        view.btnDatang.onClick {




            startActivity(intentFor<DatangActivity>().newTask())
        }



        dialog.setContentView(view)
        dialog?.show()

    }
}