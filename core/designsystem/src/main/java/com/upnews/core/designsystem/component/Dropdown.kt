package com.upnews.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.upnews.core.common.R.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    modifier: Modifier = Modifier,
    @StringRes labelId: Int? = null,
    items: List<String>,
    onSelectedItem: (Int) -> Unit = {},
    selectedItemIndex: Int = 0,
    isLoading: Boolean = false,
) {
    var expanded by remember { mutableStateOf(false) }
    val performClickItem = { item: String ->
        expanded = false
        onSelectedItem(items.indexOf(item))
    }
    val heightMenu = TextFieldDefaults.MinHeight.times(6)

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded.takeIf { !isLoading } ?: false,
        onExpandedChange = { expanded = expanded.not() },
    ) {
        DisabledFieldOutlined(
            labelId = labelId,
            value = items[selectedItemIndex],
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifierTextField = Modifier.menuAnchor(),
            isLoading = isLoading,
        )
        DropdownMenu(
            expanded = expanded.takeIf { !isLoading } ?: false,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .exposedDropdownSize()
                .height(heightMenu)
                .background(color = MaterialTheme.colorScheme.background),
            offset = DpOffset(0.dp, 4.dp),
        ) {
            items.forEach { item ->
                if (items[selectedItemIndex] != item) {
                    DropdownMenuItem(
                        onClick = { performClickItem(item) },
                        text = { Text(text = item) },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        colors = MenuDefaults.itemColors(
                            textColor = MaterialTheme.colorScheme.onBackground,
                        ),
                    )
                    if (item != items.last()) {
                        Divider(color = MaterialTheme.colorScheme.outline)
                    }
                }
            }
        }
    }
}


@Composable
internal fun FieldOutlined(
    modifier: Modifier = Modifier,
    modifierTextField: Modifier = Modifier,
    @StringRes labelId: Int?,
    value: String,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    onChangeValue: (String) -> Unit,
    maxLines: Int = 1,
) {
    Column(modifier) {
        if (labelId != null) {
            Text(text = stringResource(id = labelId), style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
        }
        OutlinedTextField(
            value = value,
            onValueChange = onChangeValue,
            shape = MaterialTheme.shapes.small,
            modifier = modifierTextField
                .fillMaxWidth(),
            maxLines = maxLines,
            readOnly = readOnly,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            enabled = enabled,
            colors = colors,
        )
    }
}

@Composable
internal fun DisabledFieldOutlined(
    modifier: Modifier = Modifier,
    modifierTextField: Modifier = Modifier,
    @StringRes labelId: Int?,
    value: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isLoading: Boolean = false,
) {
    FieldOutlined(
        labelId = labelId,
        value = value.takeIf { !isLoading } ?: stringResource(id = string.loading),
        onChangeValue = {},
        readOnly = true,
        enabled = false,
        trailingIcon = trailingIcon.takeIf { !isLoading },
        leadingIcon = leadingIcon.takeIf { !isLoading },
        modifier = modifier,
        modifierTextField = modifierTextField,
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = LocalContentColor.current,
            disabledTrailingIconColor = LocalContentColor.current,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledContainerColor = MaterialTheme.colorScheme.background.takeIf { !isLoading }
                ?: Color.LightGray.copy(.4f),
        ),
    )
}