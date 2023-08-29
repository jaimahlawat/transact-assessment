package com.transact.assessment.ui

sealed class Screen(val route: String) {
    data object Home: Screen("home")
}