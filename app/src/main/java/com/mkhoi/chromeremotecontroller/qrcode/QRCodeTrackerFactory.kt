package com.mkhoi.chromeremotecontroller.qrcode

import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.barcode.Barcode


class QRCodeTrackerFactory(private val listener: QRCodeDetectListener): MultiProcessor.Factory<Barcode> {
    override fun create(barcode: Barcode): Tracker<Barcode> {
        return QRCodeTracker(listener)
    }
}