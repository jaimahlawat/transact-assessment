package com.transact.assessment.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.transact.assessment.R

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit,
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.error_something_went_wrong))

        Spacer(modifier = Modifier.height(10.dp))

        FilledTonalButton(
            onClick = { onRetryClick() }
        ) {
            Text(text = stringResource(id = R.string.button_text_retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorViewPreview() {
    ErrorView(onRetryClick = {})
}