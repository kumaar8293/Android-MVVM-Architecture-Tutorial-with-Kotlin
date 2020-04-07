package com.mukesh.mvvmarchitecturetutorialkotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class CallBroadCast : BroadcastReceiver() {

    val TAG = "CALL BROAD CAST"
    override fun onReceive(context: Context?, intent: Intent?) {
        println(TAG)
        Toast.makeText(context, TAG, Toast.LENGTH_LONG).show()
    }
}