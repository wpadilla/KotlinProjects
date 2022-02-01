package com.example.usersp

data class User(val id: Long, var name: String, var lastName: String, var url: String) {

    fun getFullName() = "$name $lastName"
}