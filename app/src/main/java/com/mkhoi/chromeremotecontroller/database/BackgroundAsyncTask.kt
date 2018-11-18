package com.mkhoi.chromeremotecontroller.database

import android.os.AsyncTask
import android.util.Log

class BackgroundAsyncTask : AsyncTask<() -> Unit, Void, Void>() {
    private val TAG = "BackgroundAsyncTask"

    override fun doInBackground(functions: Array<() -> Unit>): Void? {
        try {
            for (function in functions) {
                function.invoke()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Fail to load data", e)
        }

        return null
    }
}
