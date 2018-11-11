package com.mkhoi.chromeremotecontroller.qrcode

import com.google.android.gms.vision.barcode.Barcode
import android.support.annotation.UiThread

interface QRCodeDetectListener {
    @UiThread
    fun onQRCodeDetected(barcode: Barcode)
}