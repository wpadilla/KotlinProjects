package com.example.updated.classes

class Smartphone(number: Int, val isPrivate: Boolean): Phone(number) {

    override fun showNumber() {
        if(isPrivate) {
            print("Numero Privado")
        } else {
            super.showNumber()
        }
    }
}