@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recharge.feature.recharge.presentation.componets

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recharge.feature.recharge.presentation.util.RechargeUiIntent
import com.example.core.sharedData.RechargeModel
import com.example.utils.core.UiState

@Composable
fun VoucherTab(
    rechargeModel: RechargeModel?,
    codeUiState: UiState<RechargeUiIntent>,
    code: String?,
    onCodeChanged: (String?) -> Unit,
) {
    var codeText by remember { mutableStateOf(code ?: "") }
    var statusUpdate by remember { mutableStateOf(RechargeUiIntent()) }
    val context = LocalContext.current

    when (codeUiState) {

        is UiState.Error -> {
            statusUpdate = RechargeUiIntent(
                isEnable = false,
                statusMsg = codeUiState.throwable?.message
            )
        }

        is UiState.Ideal -> {}

        is UiState.Loading -> {}

        is UiState.Success -> {
            codeUiState.data?.let { statusUpdate = it }
        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        rechargeModel?.let {
            RechargeDataCard(it)
        }

        OutlinedTextField(
            value = codeText,
            onValueChange = {
                if (it.length <= 15) {
                    codeText = it
                    onCodeChanged(it)
                }
            },
            label = {
                Text(text = "Enter Voucher Code")
            },

            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = Color.LightGray,
                disabledIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            visualTransformation = VisualTransformation.None,
            modifier = Modifier
                .testTag("CodeInputField")
                .padding(horizontal = 8.dp)
        )

        statusUpdate.statusMsg?.let {
            Text(
                text = it,
                color = statusUpdate.color,
                fontSize = 12.sp,
                modifier = Modifier
                    .testTag("statusText")
                    .padding(8.dp)
            )
        }


        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
            },
            enabled = statusUpdate.isEnable,
            modifier = Modifier
                .testTag("SubmitButton")
                .fillMaxWidth()
        ) {
            Text("SUBMIT")
        }
    }
}