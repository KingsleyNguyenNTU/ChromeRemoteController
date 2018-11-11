package com.mkhoi.chromeremotecontroller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.functions.FirebaseFunctions

import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = HashMap<String, String>()
        data["receiverTokenId"] = "APA91bFBTL0rg4Tw9dxRvwgWG94NHXSkg-7_Aj-E6WSSj5hXT3Z5O7E9PN6CRImtQdUaLNFqlGilI2HqslUkW7q_IK3qzewlfo1BT3pw8WrqdEMGnCivc48ynTv_FiIpYqggLvBcdcNS"
        data["command"] = "Test"
        data["optionalParameter"] = ""

        /*fab.setOnClickListener { _ ->
            FirebaseFunctions.getInstance()
                    .getHttpsCallable("sendMessage")
                    .call(data)
                    .addOnFailureListener {
                        Log.wtf("Firebase", "Fail")
                    }
                    .addOnSuccessListener {
                        Log.d("Firebase", "Done")
                    }
        }*/
    }
}
