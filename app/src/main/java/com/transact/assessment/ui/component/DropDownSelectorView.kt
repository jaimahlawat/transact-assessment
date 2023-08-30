@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.transact.assessment.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.transact.assessment.R
import com.transact.assessment.domain.model.Filter
import com.transact.assessment.util.noRippleClickable

@Composable
fun DropDownSelectorView(
    modifier: Modifier = Modifier,
    list: List<Filter>,
    selectedFilter: String?,
    onItemSelected: (Filter) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(IntSize.Zero) }

    val icon = if (isExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = modifier
            .padding(10.dp)
            .wrapContentHeight()
            .noRippleClickable {
                isExpanded = !isExpanded
            }
    ) {
        OutlinedTextField(
            value = selectedFilter ?: "",
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    size = layoutCoordinates.size
                },
            label = {
                Text(text = stringResource(id = R.string.text_filter_by_author))
            },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "dropDownIcon",
                    Modifier.clickable {
                        isExpanded = !isExpanded
                    }
                )
            }
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .width(
                    with(LocalDensity.current){
                        size.width.toDp()
                    }
                )
                .height(400.dp)
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(text = item.name)
                    },
                    onClick = {
                        isExpanded = false
                        onItemSelected(
                            item.copy(
                                isSelected = true
                            )
                        )
                    }
                )
            }
        }
    }
}