package com.example.stores

interface MainAux {
    fun hideFab(notHide: Boolean)

    fun addStore(store: StoreEntity)

    fun updateStore(store: StoreEntity)
}