package com.upnews.core.ui.card

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.upnews.core.common.util.launchCustomChromeTab
import com.upnews.core.common.util.shimmer
import com.upnews.core.model.data.Country
import com.upnews.core.model.data.Source
import com.upnews.core.ui.R

/**
 * [Source] card used on the following screens: For You, Saved
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Source.CardItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onClick: (String) -> Unit,
) {
    val clickActionLabel = stringResource(R.string.card_tap_action)
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background.toArgb()
    val resourceUrl by remember {
        mutableStateOf(Uri.parse(url))
    }

    Card(
        onClick = { onClick(id) },
        enabled = !isLoading,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface),
        elevation = CardDefaults.cardElevation(2.dp),
        // Use custom label for accessibility services to communicate button's action to user.
        // Pass null for action to only override the label and not the actual action.
        modifier = modifier.semantics {
            onClick(label = clickActionLabel, action = null)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = name, style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer(isLoading)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                LabelInfo(
                    modifier = Modifier.weight(1f),
                    isLoading = isLoading,
                    label = stringResource(id = R.string.category), value = category?.value ?: "-",
                )
                LabelInfo(
                    modifier = Modifier.weight(1f),
                    isLoading = isLoading,
                    label = stringResource(id = R.string.country), value = Country.values().find { it.code == country }?.value ?: "-"
                )
                LabelInfo(
                    modifier = Modifier.weight(1f),
                    isLoading = isLoading,
                    label = stringResource(id = R.string.language), value = language,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (isLoading) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .shimmer(true))
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .shimmer(true))
            } else {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (url.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.visit_page),
                    modifier = Modifier
                        .shimmer(isLoading)
                        .clickable(!isLoading) {
                            launchCustomChromeTab(
                                context,
                                resourceUrl,
                                backgroundColor
                            )
                        },
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = if (!isLoading) Color.Blue else Color.Unspecified
                    ),
                )
            }
        }
    }
}

@Composable
fun LabelInfo(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    isLoading: Boolean = false,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.shimmer(isLoading)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .shimmer(isLoading)
        )
    }
}