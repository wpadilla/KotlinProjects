package com.example.updated

import com.example.updated.classes.Group
import com.example.updated.classes.User
import newTopic

fun main() {
    newTopic("Collections")
    val fruitList = listOf("Freza", "Mango", "Limo")

    println(fruitList.get((0..fruitList.size -1).random()))
    println(" Fezas's index ${fruitList.indexOf("Freza")}")

    newTopic("Mutable List");
    val user = User(1, "Williams", "Padilla", Group.FRIENDS.ordinal)

    val bro = user.copy(9, "Peter")
    val userList = mutableListOf<User>(user, bro);
    val friend = bro.copy(10);

    userList.add(friend)
    userList.remove(bro)
    println(userList);

    val userSelectedList = mutableListOf<User>()
    println(userSelectedList)

    userSelectedList.add(user)
    userSelectedList.add(user)
    userSelectedList.add(user)
    userSelectedList.set(0, bro)

    println(userSelectedList)

    val userMap = mutableMapOf<Int, User>()
    println(userMap)
    userMap.put(user.id.toInt(), user);
    userMap.put(friend.id.toInt(), friend);
    println(userMap)
    userMap.remove(10)
    println(userMap)
    println(userMap.isEmpty())
    println(userMap.containsKey(1))
    userMap.put(bro.id.toInt(), bro)
    userMap.put(friend.id.toInt(), friend)
    println(userMap);
    println(userMap.keys)
    println(userMap.values)

}