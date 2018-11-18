package com.mkhoi.chromeremotecontroller

import android.support.annotation.IdRes

enum class Command(@IdRes val buttonId: Int,
                   val optionalParameter: String = "") {
    TURN_OFF(R.id.turnOffButton),
    INCREASE_VOLUME(R.id.increaseVolumeButon),
    REDUCE_VOLUME(R.id.decreaseVolumeButton),
    MUTE(R.id.muteButton),
    NEW_TAB(R.id.newPageButton),
    NEXT_TAB(R.id.nextTabButton),
    PREVIOUS_TAB(R.id.previousTabButton),
    LAST_30S(R.id.rewind30sButton),
    NEXT_30S(R.id.forward30sButton),
    REWIND(R.id.fastRewindButton),
    FORWARD(R.id.fastForwardButton),
    PLAY(R.id.playButton);

    companion object {
        fun getCommandByViewId(@IdRes buttonId: Int): Command? = Command.values().find { it.buttonId == buttonId }
    }
}