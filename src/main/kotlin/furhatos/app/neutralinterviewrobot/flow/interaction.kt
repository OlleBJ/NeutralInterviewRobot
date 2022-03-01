package furhatos.app.neutralinterviewrobot.flow

import furhatos.app.neutralinterviewrobot.nlu.*
import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures

val Start : State = state(Interaction) {

    onEntry {
        furhat.ask({
            +"Hello!"
            +"How are you today?" })
    }


    onResponse<BadAndYou>{
        furhat.say("I am a robot, I do not have any feelings.")
        goto(Problem)
    }
    onResponse<HowRU>{
        furhat.say("I am a robot, I do not have any feelings.")
        goto(Visitors)
    }

    onResponse<Good>{
        furhat.say {
            +"Ok."
        }
        goto(Visitors)
    }

    onResponse<Bad>{
        furhat.say("Ok.")
        goto(Problem)
    }
    onResponse<Hobby>{
        furhat.say("${it.text}, i see." )
        goto(Visitors)
    }
}

val Problem : State = state(Interaction){
    onEntry{
        furhat.ask("What is wrong?")
    }
    onResponse<Pain>{
        goto(Doctor)
    }
    onResponse<Lonely>{
        goto(Family)
    }
    onResponse<Tired>{
        furhat.say{
            +"I see."
        }
        goto(Visitors)
    }
}

val Family : State = state(Interaction){
    onEntry{
        furhat.ask("I see. Would you like to call your family?")
    }
    onResponse<Yes>{
        furhat.say("I will schedule a phone call for you for later today.")
        goto(Visitors)
    }
    onResponse<No>{
        furhat.say{
            +"Ok."
        }
        goto(Visitors)
    }
}

val Doctor : State = state(Interaction){
    onEntry{
        furhat.ask("I see. Would you like to see a doctor?")
    }
    onResponse<Yes>{
        furhat.say{
            +"I will schedule an appointment for you for later today."
        }
        goto(Visitors)
    }
    onResponse<No>{
        furhat.say("Ok.")
        goto(Visitors)
    }
}

val Visitors : State = state(Interaction){
    onEntry{
        delay(1000)
        furhat.ask("Have you had any visitors or spoken to family lately?")
    }
    onResponse<Yes>{
        goto(HadVisitors)
    }
    onResponse<No>{
        goto(HadNoVisitors)
    }
}

val HadVisitors : State = state(Interaction){
    onEntry{
        furhat.ask("Did you have a good time?")
    }
    onResponse<Yes>{
        furhat.say("Ok.")
        goto(Exercise)

    }
    onResponse<No>{
        furhat.say("Ok.")
        goto(Exercise)
    }
}

val HadNoVisitors : State = state(Interaction){
    onEntry{
        furhat.ask("Would you like to have visits more often?")
    }
    onResponse<Yes>{
        furhat.say("I will let the members of staff know so that they can contact your family.")
        goto(Exercise)

    }
    onResponse<No>{
        furhat.say("Ok.")
        goto(Exercise)
    }
}

val Exercise : State = state(Interaction){
    onEntry{
        delay(1000)
        furhat.ask("Did you exercise this week?")
    }
    onResponse<Yes>{
        goto(WhatExercise)
    }
    onResponse<No>{
        goto(WalkWithStaff)
    }
    onResponse<Exercises>{
        furhat.say("${it.text}, ok")
        goto(Sleep)
    }
}

val WhatExercise: State = state(Interaction){
    onEntry{
        furhat.ask("What did you do?")
    }
    onResponse{
        furhat.say("Ok.")
        goto(Sleep)
    }
}

val WalkWithStaff: State = state(Interaction){
    onEntry{
        furhat.ask("Would you like to take a walk with someone from the staff?")
    }
    onResponse<Yes>{
        furhat.say("I will let the staff know so that it can be arranged.")
        goto(Sleep)
    }
    onResponse<No>{
        furhat.say("Ok")
        goto(Sleep)
    }
}

val Sleep : State = state(Interaction){
    onEntry{
        delay(1000)
        furhat.ask("Have you been sleeping well this last week?")
    }
    onResponse<Yes>{
        furhat.say("I see.")
        goto(Olympics)
    }
    onResponse<No>{
        goto(BadSleep)
    }
}

val BadSleep : State = state(Interaction){
    onEntry{
        furhat.ask("Do you think it is temporary or should I contact the " +
                "manager so we can figure out how to best help you?")
    }
    onResponse<Yes>{
        furhat.say("Okay, I will let the manager know about your problems, good sleep is very important. ")
        goto(Olympics)
    }
    onResponse<No>{
        furhat.say("Okay, if it persists, you can always tell me next time or contact a member of staff. It is important to sleep well. ")
        goto(Olympics)
    }
}

val Olympics : State = state(Interaction){
    onEntry{
        delay(1000)
        furhat.ask("Did you watch the Olympics on TV?")
    }
    onResponse<Yes>{
        furhat.say("I see.")
        goto(Food)
    }
    onResponse<No>{
        goto(NoOlympics)
    }
}

val NoOlympics : State = state(Interaction){
    onEntry{
        furhat.ask("Do you want to know how many medals Sweden won?")
    }
    onResponse<Yes>{
        furhat.say("Sweden won a total of 18 medals, 8 gold medals, 5 silver and 5 bronze.")
        goto(Food)
    }
    onResponse<No>{
        furhat.say("Ok")
        goto(Food)
    }
}

val Food : State = state(Interaction){
    onEntry{
        delay(1000)
        furhat.ask("Have you liked the food here lately?")
    }
    onResponse<Yes>{
        furhat.say("Ok.")
        goto(Activity)
    }
    onResponse<No>{
        goto(BadFood)
    }
}

val BadFood : State = state(Interaction){
    onEntry{
        furhat.ask("Ok! Does this affect your appetite?")
    }
    onResponse{
        goto(RequestMeal)
    }

}

val RequestMeal : State = state(Interaction){
    onEntry{
        furhat.ask("I see. Would you like to request any specific meal?")
    }
    onResponse<Yes>{
        goto(FoodOrder)
    }
    onResponse<No>{
        furhat.say("Ok.")
        goto(Activity)
    }
}

val FoodOrder : State = state(Interaction){
    onEntry{
        furhat.ask("Ok. What would you like to have?")
    }
    onResponse{
        furhat.say("I will let the cook know.")
        goto(Activity)
    }

}

val Activity : State = state(Interaction){
    onEntry{
        delay(1000)
        furhat.ask("What kind of activity would you like to do for this week?")
    }
    onResponse<RequestOptions> {
        furhat.say("We have ${Activity().optionsToText()}")
        furhat.ask("Which one do you prefer?")
    }
    onResponse<Activity>{
        furhat.say("Ok. It will be brought to you later today.")
        goto(Game)
    }
    onResponse{
        furhat.say("I am not sure we can provide that. We have ${Activity().optionsToText()}")
        furhat.ask("Would you like any of these?")
    }
    onResponse<None>{
        furhat.say("Ok, you will get the chance to pick a new one next week")
        goto(Game)
    }
}


val Game : State = state(Interaction){
    onEntry{
        delay(1000)
        furhat.ask("Would you be interested in playing a game with me later?")
    }
    onResponse<RequestOptions> {
        furhat.say("We have ${Game().optionsToText()}")
        furhat.ask("Which one do you prefer?")
    }
    onResponse<Game>{
        furhat.say("${it.text}, Ok.")
        goto(EndInterview)
    }
    onResponse<Yes>{
        furhat.say("Ok. We have ${Game().optionsToText()}")
        furhat.ask("Which one would you like to play?")
    }
    onResponse<No>{
        furhat.say("Ok, I will ask you again next week in case you have changed your mind.")
        goto(EndInterview)
    }
    onResponse{
        furhat.say("Unfortunately we do not have that game. We have ${Game().optionsToText()}")
        furhat.ask("Which one would you like to play?")
    }
}


val EndInterview : State = state(Interaction){
    onEntry{
        delay(1000)
        furhat.say("We have now reached the end of this interview. If there is something specific " +
                "that you would like to talk about next time, please let the staff know.")
    }

}