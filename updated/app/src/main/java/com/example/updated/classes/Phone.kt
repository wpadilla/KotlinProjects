package com.example.updated.classes

open class Phone (protected val number: Int) {

    fun call() {
        println("Calling...")
    }

    open fun showNumber() {
        println("My Number is $number")
    }
}

