package com.example.recharge.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.ui.component.scaffold.ZainScaffold
import com.example.recharge.R
import com.example.recharge.domain.enums.TabSelection.VOUCHER
import com.example.sharedData.model.RechargeModel
import com.example.recharge.domain.enums.TabSelection.BALANCE
import com.example.recharge.presentation.componets.BalanceTab
import com.example.recharge.presentation.componets.VoucherTab
import com.example.recharge.presentation.util.RechargeUiIntent
import com.example.utils.core.UiState


@Composable
internal fun RechargeRoute(
    onBackClick: () -> Unit,
    rechargeModel: RechargeModel?,
    rechargeViewModel: RechargeViewModel = hiltViewModel()
) {

    val amountUiState by rechargeViewModel.amountUiState.collectAsStateWithLifecycle()
    val codeUiState by rechargeViewModel.codeUiState.collectAsStateWithLifecycle()
    val amount by rechargeViewModel.amountSavedState.collectAsStateWithLifecycle()
    val code by rechargeViewModel.codeSavedState.collectAsStateWithLifecycle()


    RechargeScreen(
        rechargeModel = rechargeModel,
        onBackClick = onBackClick,
        amount = amount,
        amountUiState = amountUiState,
        codeUiState = codeUiState,
        code = code,
        onAmountChanged = rechargeViewModel::onAmountChanged,
        onCodeChanged = rechargeViewModel::onCodeChanged
    )
}


@Composable
fun RechargeScreen(
    onBackClick: () -> Unit,
    rechargeModel: RechargeModel?,
    amountUiState: UiState<RechargeUiIntent>,
    codeUiState: UiState<RechargeUiIntent>,
    amount: Int,
    code: String?,
    onAmountChanged: (Int) -> Unit,
    onCodeChanged: (String?) -> Unit
) {

    ZainScaffold(
        title = stringResource(id = R.string.recharge_screen),
        navIcon = {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Person Icon",
                modifier = Modifier.clickable {
                    onBackClick()
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(MaterialTheme.colorScheme.onPrimary)
            ) {


                var selectedTabIndex by remember { mutableStateOf(BALANCE.index) }


                Column {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(8.dp), // Set elevation here
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        TabRow(
                            selectedTabIndex = selectedTabIndex
                        ) {
                            Tab(
                                selected = selectedTabIndex == BALANCE.index,
                                onClick = { selectedTabIndex = BALANCE.index }
                            ) {
                                Text(text = "Balance", modifier = Modifier.padding(16.dp))
                            }
                            Tab(
                                selected = selectedTabIndex == VOUCHER.index,
                                onClick = { selectedTabIndex = VOUCHER.index }
                            ) {
                                Text(text = "Voucher", modifier = Modifier.padding(16.dp))
                            }
                        }
                    }

                    when (selectedTabIndex) {
                        BALANCE.index ->
                            BalanceTab(
                                rechargeModel = rechargeModel,
                                amountUiState = amountUiState,
                                amount = amount,
                                onAmountChanged = onAmountChanged
                            )

                        VOUCHER.index ->
                            VoucherTab(
                                rechargeModel = rechargeModel,
                                codeUiState = codeUiState,
                                code = code,
                                onCodeChanged = onCodeChanged
                            )
                    }
                }

            }
        }
    )
}
