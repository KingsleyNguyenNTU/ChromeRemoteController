package com.mkhoi.chromeremotecontroller

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.firebase.functions.FirebaseFunctions
import com.mkhoi.chromeremotecontroller.qrcode.QRCodeReaderActivity
import com.mkhoi.chromeremotecontroller.qrcode.QRCodeReaderActivity.Companion.DATA_TOKEN_ID_KEY
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val TOKEN_ID_FROM_CAMERA_REQUEST_CODE = 2

        const val FIREBASE_DATA_TOKEN_KEY = "receiverTokenId"
        const val FIREBASE_DATA_COMMAND_KEY = "command"
        const val FIREBASE_DATA_OPTIONAL_PARAM_KEY = "optionalParameter"
        const val FIREBASE_CLOUD_FUNCTION_NAME = "sendMessage"
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModelObserver()
    }

    @SuppressLint("InflateParams")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TOKEN_ID_FROM_CAMERA_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val tokenId = it.getStringExtra(DATA_TOKEN_ID_KEY)

                    val dialogView = layoutInflater.inflate(R.layout.input_pc_name_dialog, null)
                    val pcNameEditText = dialogView.findViewById<EditText>(R.id.input_pc_name_edit_text)

                    val builder = android.app.AlertDialog.Builder(this)
                    builder.setTitle(R.string.input_pc_name_title_dialog)
                            .setView(dialogView)
                            .setPositiveButton(R.string.ok_btn_label){_,_ ->
                                viewModel.updateSelectedPc(this, pcNameEditText.text.toString(), tokenId)
                            }
                            .setNegativeButton(R.string.cancel_btn_label){ dialog, _ -> dialog.cancel()}
                            .show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
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
                                startActivityForResult(intent, TOKEN_ID_FROM_CAMERA_REQUEST_CODE)
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

    fun onRemoteButtonClick(view: View){
        Command.getCommandByViewId(view.id)?.let {
            sendCommandMessage(it.name, it.optionalParameter)
        }
    }

    private fun sendCommandMessage(command: String, optionalParameter: String){
        viewModel.selectedPC.value?.let {
            val data = HashMap<String, String>()
            data[FIREBASE_DATA_TOKEN_KEY] = it.tokenId
            data[FIREBASE_DATA_COMMAND_KEY] = command
            data[FIREBASE_DATA_OPTIONAL_PARAM_KEY] = optionalParameter

            FirebaseFunctions.getInstance()
                    .getHttpsCallable(FIREBASE_CLOUD_FUNCTION_NAME)
                    .call(data)
                    .addOnFailureListener {
                        Log.wtf("Firebase", "Fail")
                    }
                    .addOnSuccessListener {
                        Log.d("Firebase", "Done")
                    }
        }
    }
}
