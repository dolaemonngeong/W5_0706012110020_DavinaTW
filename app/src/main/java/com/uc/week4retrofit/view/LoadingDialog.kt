package com.uc.week4retrofit.view

import android.app.Activity
import android.app.AlertDialog
import com.uc.week4retrofit.R


class LoadingDialog (val mActivity: Activity) {
    private lateinit var isDialog:AlertDialog

    fun startLoading(){
        //set view
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_templete, null)

        //set dialog
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun isDismiss(){
        isDialog.dismiss()
    }
}