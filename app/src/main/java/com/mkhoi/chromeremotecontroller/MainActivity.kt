package com.mkhoi.chromeremotecontroller

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.functions.FirebaseFunctions
import com.mkhoi.chromeremotecontroller.qrcode.QRCodeReaderActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModelObserver()
        initButtonListener()
    }

    private fun initViewModelObserver(){
        viewModel = ViewModelProviders.of(this, MainViewModel.Factory()).get(MainViewModel::class.java)

        viewModel.connectedPCs.observe(this, Observer {
            it?.let {devices ->
                val pcNames = devices.map { it.name }.toTypedArray()
                selectConnectedPCButton.setOnClickListener {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(R.string.pick_pc_dialog_title)

                            .setPositiveButton(R.string.add_btn_label) { _, _ ->
                                val intent = Intent(this, QRCodeReaderActivity::class.java)
                                startActivity(intent)
                            }
                            .setNegativeButton(R.string.cancel_btn_label){ dialog, _ -> dialog.cancel()}

                    if (pcNames.isEmpty()){
                        builder.setMessage(R.string.no_connected_pc_msg_dialog)
                    } else {
                        builder.setItems(pcNames) { _, which ->
                            viewModel.selectedPC.value = devices[which]
                        }
                    }

                    builder.create()
                    builder.show()
                }
            }
        })

        viewModel.selectedPC.observe(this, Observer {
            it?.let {
                selectConnectedPCButton.text = it.name
            }
        })
    }

    private fun initButtonListener(){

    }

    private fun sendCommandMessage(){
        val data = HashMap<String, String>()
        data["receiverTokenId"] = "APA91bFBTL0rg4Tw9dxRvwgWG94NHXSkg-7_Aj-E6WSSj5hXT3Z5O7E9PN6CRImtQdUaLNFqlGilI2HqslUkW7q_IK3qzewlfo1BT3pw8WrqdEMGnCivc48ynTv_FiIpYqggLvBcdcNS"
        data["command"] = "Test"
        data["optionalParameter"] = ""

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
