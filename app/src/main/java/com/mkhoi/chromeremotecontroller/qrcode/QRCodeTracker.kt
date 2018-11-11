package com.mkhoi.chromeremotecontroller.qrcode

import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.barcode.Barcode


class QRCodeTracker(private val listener: QRCodeDetectListener): Tracker<Barcode>() {
    override fun onNewItem(id: Int, item: Barcode) {
        listener.onQRCodeDetected(item)
    }
}