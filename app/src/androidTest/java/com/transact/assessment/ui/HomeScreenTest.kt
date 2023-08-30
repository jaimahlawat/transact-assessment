package com.transact.assessment.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.transact.assessment.ui.home.HomeScreen
import com.transact.assessment.ui.theme.AssessmentTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun screenTest() {
        composeTestRule.setContent {
            AssessmentTheme {
                HomeScreen()
            }
        }

        composeTestRule.onNodeWithText("Clear").assertIsDisplayed()
    }
}