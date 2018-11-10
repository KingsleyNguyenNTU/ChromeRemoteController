package com.mkhoi.chromeremotecontroller.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class PCEntity(@PrimaryKey(autoGenerate = true) var id: Int? = null,
                    var name: String,
                    var tokenId: String)