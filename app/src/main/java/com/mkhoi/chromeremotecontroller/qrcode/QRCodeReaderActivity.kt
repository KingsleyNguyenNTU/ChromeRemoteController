package com.mkhoi.chromeremotecontroller.qrcode

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.SurfaceHolder
import android.widget.EditText
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.mkhoi.chromeremotecontroller.MainActivity
import com.mkhoi.chromeremotecontroller.R
import kotlinx.android.synthetic.main.activity_qrcode_reader.*


class QRCodeReaderActivity : AppCompatActivity(), QRCodeDetectListener {

    companion object {
        const val CAMERA_REQUEST_CODE = 1
        const val DATA_TOKEN_ID_KEY = "DATA_TOKEN_ID_KEY"
    }

    override fun onQRCodeDetected(barcode: Barcode) {
        Log.d("QRCodeReaderActivity", barcode.displayValue)
        val intent = Intent()
        intent.putExtra(DATA_TOKEN_ID_KEY, barcode.displayValue)
        setResult(RESULT_OK, intent)
        finish()
    }

    private var cameraSource: CameraSource? = null
    var surfaceHolder: SurfaceHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_reader)

        surfaceHolder = surfaceView.holder

        val rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource()
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_REQUEST_CODE)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) cameraSource?.start(surfaceHolder)
    }

    override fun onPause() {
        super.onPause()
        cameraSource?.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource?.release()
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != CAMERA_REQUEST_CODE) {
            Log.d("QRCodeReaderActivity", "Got unexpected permission result: $requestCode")
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("QRCodeReaderActivity", "Camera permission granted - initialize the camera source")
            createCameraSource()
            cameraSource?.start(surfaceHolder)
            return
        } else {
            Log.d("QRCodeReaderActivity", "Camera permission denied. Closed the app")
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.no_camera_permission)
                    .setPositiveButton(R.string.retry_btn_label){_,_ ->
                        ActivityCompat.requestPermissions(this,
                                arrayOf(Manifest.permission.CAMERA),
                                CAMERA_REQUEST_CODE)
                    }
                    .setNegativeButton(R.string.cancel_btn_label){ dialog, _ -> dialog.cancel()}
                    .show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun createCameraSource() {
        val barcodeDetector = BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build()

        barcodeDetector.setProcessor(
                MultiProcessor.Builder<Barcode>(QRCodeTrackerFactory(this)).build())

        cameraSource = CameraSource.Builder(applicationContext, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true)
                .setRequestedFps(15.0f)
                .build()
    }
}
