package com.example.recharge.feature.recharge.presentation.componets


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import com.example.recharge.feature.recharge.domain.enums.SuggestedAmount.AMOUNT100
import com.example.recharge.feature.recharge.domain.enums.SuggestedAmount.AMOUNT200
import com.example.recharge.feature.recharge.domain.enums.SuggestedAmount.AMOUNT300
import com.example.recharge.feature.recharge.domain.enums.SuggestedAmount.AMOUNT50
import com.example.recharge.feature.recharge.presentation.util.RechargeUiIntent
import com.example.core.sharedData.RechargeModel
import com.example.utils.core.UiState

@Composable
fun BalanceTab(
    rechargeModel: RechargeModel?,
    amountUiState: UiState<RechargeUiIntent>,
    amount: String?,
    onAmountChanged: (String?) -> Unit
) {

    var searchText by remember { mutableStateOf(amount) }
    var statusUpdate by remember { mutableStateOf(RechargeUiIntent()) }
    val context = LocalContext.current

    when (amountUiState) {

        is UiState.Error -> {
            statusUpdate  = RechargeUiIntent(isEnable = false)
        }
        is UiState.Ideal -> {}

        is UiState.Loading -> {}

        is UiState.Success -> {
            amountUiState.data?.let { statusUpdate  = it }
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
            value = searchText ?: "",
            onValueChange = {
                if (it.length < 6) {
                    searchText = it
                    onAmountChanged(it)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),

            trailingIcon = {
                Text(
                    text = "SAR",
                    color = Color.Gray
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.primary),
            visualTransformation = VisualTransformation.None,
            modifier = Modifier
                .testTag("AmountInputField")
                .width(150.dp)
                .padding(horizontal = 8.dp)
        )


        Text(
            text = "15% VAT Exclusive",
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    searchText = AMOUNT50.amount
                    onAmountChanged(AMOUNT50.amount)
                },
                modifier = Modifier
                    .padding(4.dp)
                    .testTag("Amount50")
            ) {
                Text(AMOUNT50.amount)
            }
            Button(
                onClick = {
                    searchText = AMOUNT100.amount
                    onAmountChanged(AMOUNT100.amount)
                },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(AMOUNT100.amount)
            }
            Button(
                onClick = {
                    searchText = AMOUNT200.amount
                    onAmountChanged(AMOUNT200.amount)
                },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(AMOUNT200.amount)
            }
            Button(
                onClick = {
                    searchText = AMOUNT300.amount
                    onAmountChanged(AMOUNT300.amount)
                },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(AMOUNT300.amount)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Charged", Toast.LENGTH_SHORT).show()
            },
            enabled = statusUpdate.isEnable,
            modifier = Modifier
                .testTag("ChargeButton")
                .fillMaxWidth()

        )
        {
            Text("PAY WITH CARD")
        }
    }
}
