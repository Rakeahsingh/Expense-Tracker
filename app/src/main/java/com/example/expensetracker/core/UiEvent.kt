package com.example.expensetracker.core

sealed class UiEvent {

    data class Alert(val info: String): UiEvent()
    data class NoAlert(val info: String = String()) : UiEvent()

}