package furhatos.app.neutralinterviewrobot.flow

import furhatos.autobehavior.setDefaultMicroexpression
import furhatos.flow.kotlin.*
import furhatos.autobehavior.*
import furhatos.flow.kotlin.voice.PollyVoice

val Idle: State = state {

    init {
        // set the face and voice of the robot and tweak end silence
        furhat.voice = PollyVoice.Amy()
        furhat.param.endSilTimeout = 1000   // increased from default 800ms
        furhat.setTexture("Isabel")
        // remove facial movements from micro expressions to make more neutral
        furhat.setDefaultMicroexpression(blinking = true, facialMovements= false, eyeMovements = true)
        // Make no gesture at speech start (empty list):
        furhat.userSpeechStartGesture = listOf()
        // Make no gesture at prominence (empty list):
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