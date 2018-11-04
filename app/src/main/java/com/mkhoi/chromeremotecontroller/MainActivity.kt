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
        setSupportActionBar(toolbar)

        val data = HashMap<String, String>()
        data["receiverTokenId"] = "APA91bFBTL0rg4Tw9dxRvwgWG94NHXSkg-7_Aj-E6WSSj5hXT3Z5O7E9PN6CRImtQdUaLNFqlGilI2HqslUkW7q_IK3qzewlfo1BT3pw8WrqdEMGnCivc48ynTv_FiIpYqggLvBcdcNS"
        data["command"] = "Test"
        data["optionalParameter"] = ""

        fab.setOnClickListener { _ ->
            FirebaseFunctions.getInstance()
                    .getHttpsCallable("sendMessage")
                    .call(data)
                    .addOnFailureListener {
                        Log.wtf("Firebase", "Fail")
                    }
                    .addOnSuccessListener {
                        Log.d("Firebase", "Done")
                    }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
