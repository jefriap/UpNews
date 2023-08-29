package com.upnews.core.ui.layout

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.upnews.core.ui.R
import com.upnews.core.common.R as RCommon


@Composable
fun LayoutIllustration(
    modifier: Modifier = Modifier,
    scrollState: ScrollState? = null,
    title: String,
    desc: String,
    @DrawableRes image: Int = R.drawable.il_empty,
    onClickAction: (() -> Unit)? = null,
    labelAction: String = stringResource(id = RCommon.string.try_again),
    containerColorAction: Color = MaterialTheme.colorScheme.primary,
    contentColorAction: Color = Color.White,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
) {
    Column(
        modifier = modifier
            .background(color = Color.White)
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .apply {
                if (scrollState != null)
                    verticalScroll(scrollState)
            },
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Empty Data",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = desc,
            style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
        )

        if (onClickAction != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onClickAction,
                contentPadding = PaddingValues(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = containerColorAction,
                    contentColor = contentColorAction
                ),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.width(160.dp)
            ) {
                Text(
                    text = labelAction,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = contentColorAction
                )
            }
        }
    }
}