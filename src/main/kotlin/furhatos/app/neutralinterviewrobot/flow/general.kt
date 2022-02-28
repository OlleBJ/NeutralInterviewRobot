package furhatos.app.neutralinterviewrobot.flow

import furhatos.autobehavior.setDefaultMicroexpression
import furhatos.flow.kotlin.*
import furhatos.autobehavior.*

import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.util.*

val Idle: State = state {

    init {
        furhat.voice = PollyVoice.Amy()
        furhat.setTexture("Isabel")
        furhat.setDefaultMicroexpression(blinking = true, facialMovements= false, eyeMovements = true)
        furhat.userSpeechStartGesture = listOf()
        furhat.prominenceGesture = listOf()


        if (users.count > 0) {
            furhat.attend(users.random)
            goto(Start)
        }
    }

    onEntry {
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(Start)
    }
}


val Interaction: State = state {

    onUserLeave(instant = true) {
        if (users.count > 0) {
            if (it == users.current) {
                furhat.attend(users.other)
                goto(Start)
            } else {
                furhat.glance(it)
            }
        } else {
            goto(Idle)
        }
    }

    onUserEnter(instant = true) {
        furhat.glance(it)
    }

}