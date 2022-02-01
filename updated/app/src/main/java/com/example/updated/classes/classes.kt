package com.example.updated.classes
import newTopic

fun main() {
    newTopic("Classes")

    val phone: Phone = Phone(809444444)

    phone.call()
    phone.showNumber()

    newTopic("Herencia")

    val smartphone = Smartphone(999999999, true)

    smartphone.call();
    smartphone.showNumber()

    newTopic("Data classes")
    val user = User(1, "Williams", "Padilla", Group.FRIENDS.ordinal)

    val bro = user.copy(9, "Peter")
    println(user.component3())
    println(bro);
    val friend = bro.copy(10);

    newTopic("Reach Function")

    with(smartphone){
        println("private? $isPrivate")
        call()
        showNumber()
    }


    friend.apply {
        group = Group.WORK.ordinal
        name = "Williams"
    }

}