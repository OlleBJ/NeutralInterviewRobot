package furhatos.app.neutralinterviewrobot

import furhatos.app.neutralinterviewrobot.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class NeutralinterviewrobotSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
