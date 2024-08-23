package com.example.recharge.feature.recharge.presentation.screens

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
import com.example.recharge.feature.recharge.domain.enums.TabSelection.VOUCHER
import com.example.core.sharedData.RechargeModel
import com.example.recharge.feature.recharge.domain.enums.TabSelection.BALANCE
import com.example.recharge.feature.recharge.presentation.componets.BalanceTab
import com.example.recharge.feature.recharge.presentation.componets.VoucherTab
import com.example.recharge.feature.recharge.presentation.util.RechargeUiIntent
import com.example.utils.core.UiState


/**
 * RechargeRoute is a composable function that sets up the recharge screen with the provided ViewModel.
 * It collects UI state and data from the ViewModel and passes them to the `RechargeScreen` composable.
 *
 * @param onBackClick Callback invoked when the back button is clicked.
 * @param rechargeModel Optional model for displaying recharge data.
 * @param rechargeViewModel The ViewModel used to manage the recharge screen state.
 */
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


/**
 * RechargeScreen is a composable function that displays the recharge UI with tabs for balance and voucher.
 * It uses `ZainScaffold` to provide the layout and navigation, and manages tab selection and content display.
 *
 * @param onBackClick Callback invoked when the back button is clicked.
 * @param rechargeModel Optional model for displaying recharge data.
 * @param amountUiState The current UI state for amount validation.
 * @param codeUiState The current UI state for code validation.
 * @param amount Current amount input value.
 * @param code Current voucher code input value.
 * @param onAmountChanged Callback invoked when the amount changes.
 * @param onCodeChanged Callback invoked when the code changes.
 */
@Composable
fun RechargeScreen(
    onBackClick: () -> Unit,
    rechargeModel: RechargeModel?,
    amountUiState: UiState<RechargeUiIntent>,
    codeUiState: UiState<RechargeUiIntent>,
    amount: String?,
    code: String?,
    onAmountChanged: (String?) -> Unit,
    onCodeChanged: (String?) -> Unit
) {

    ZainScaffold(
        title = stringResource(id = com.example.recharge.feature.recharge.R.string.recharge_screen),
        navIcon = {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Back",
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
