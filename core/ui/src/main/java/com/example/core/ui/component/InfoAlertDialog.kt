package com.example.core.ui.component

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun InfoDialog(
    onConfirm: () -> Unit,
    visibility: Boolean = true,
    info: String? = null
) {
    var isDialogVisible by remember {mutableStateOf(visibility)}

    if (isDialogVisible) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "\uD83D\uDC4B") },
            text = {
                info?.let {
                    Text(text =  it)
                }
            },
            modifier = Modifier.wrapContentWidth(),
            dismissButton = {},
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm()
                        isDialogVisible = false
                    }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
}
