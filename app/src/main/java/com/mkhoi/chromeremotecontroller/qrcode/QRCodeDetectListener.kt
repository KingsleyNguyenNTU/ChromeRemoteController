package com.mkhoi.chromeremotecontroller.qrcode

import android.support.annotation.UiThread
import com.google.android.gms.vision.barcode.Barcode

interface QRCodeDetectListener {
    @UiThread
    fun onQRCodeDetected(barcode: Barcode)
}