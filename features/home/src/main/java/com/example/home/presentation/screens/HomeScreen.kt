@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.home.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.ui.component.AppLoading
import com.example.core.ui.component.InfoDialog
import com.example.core.ui.component.scaffold.ZainScaffold
import com.example.core.sharedData.RechargeModel
import com.example.home.presentation.utils.UiIntent
import com.example.recharge.feature.home.R
import com.example.utils.core.ActionState
import com.example.utils.core.Constant.PHONE_MAX_LENGTH
import com.example.utils.core.UiState
import com.example.utils.core.toJsonString

@Composable
internal fun HomeScreenRoute(
    onNextClick: (String) -> Unit,
    searchViewModel: HomeViewModel = hiltViewModel(),
) {

    val searchResultUiState by searchViewModel.searchResultUiState.collectAsStateWithLifecycle()
    val actionState by searchViewModel.actionState.collectAsStateWithLifecycle()
    val searchQuery by searchViewModel.phoneNumber.collectAsStateWithLifecycle()


    HomeSearchScreen(
        onNextClick = onNextClick,
        searchQuery = searchQuery,
        actionState= actionState,
        searchResultUiState = searchResultUiState,
        onSearchQueryChanged = searchViewModel::onSearchQueryChanged,
        onActionStateChanged = searchViewModel::onActionStateChanged
    )
}

@Composable
fun HomeSearchScreen(
    onNextClick: (String) -> Unit,
    searchQuery: String,
    actionState: ActionState,
    searchResultUiState: UiState<RechargeModel>,
    onSearchQueryChanged: (String?) -> Unit = {},
    onActionStateChanged: (ActionState) -> Unit,
    ) {

    var statusUpdate by remember { mutableStateOf(UiIntent()) }

    ZainScaffold(
        title = stringResource(id = R.string.screen_title),
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(MaterialTheme.colorScheme.onPrimary)
            ) {


                VoiceDataFiberCard(
                    onNextClick = onNextClick,
                    searchQuery = searchQuery,
                    actionState= actionState,
                    statusUpdate = statusUpdate,
                    onSearchQueryChanged = onSearchQueryChanged,
                    onActionStateChanged = onActionStateChanged
                )


                when (searchResultUiState) {

                    is UiState.Error -> {
                        statusUpdate  = UiIntent(
                            text = searchResultUiState.throwable?.message ?: "unknown error",
                            color = Color.Red
                        )
                    }

                    is UiState.Ideal -> {
                        statusUpdate  = UiIntent()
                    }

                    is UiState.Loading -> {
                        AppLoading(isLoading = searchResultUiState.isLoading)
                    }

                    is UiState.Success -> {

                        statusUpdate  = UiIntent(
                            text = stringResource(id = R.string.valid_number),
                            color = Color.Green,
                            rechargeModel = searchResultUiState.data
                        )

                    }

                }

            }


        }
    )


}


@Composable
fun VoiceDataFiberCard(
    onNextClick: (String) -> Unit,
    searchQuery: String,
    actionState: ActionState,
    statusUpdate: UiIntent,
    onSearchQueryChanged: (String?) -> Unit,
    onActionStateChanged: (ActionState) -> Unit,
    ) {
    var selectedTab by remember { mutableStateOf("Voice") }
    var searchText by remember { mutableStateOf(searchQuery) }
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                TabButton("Voice", selectedTab) { selectedTab = "Voice" }
                Spacer(modifier = Modifier.width(8.dp))
                TabButton("Data", selectedTab) { selectedTab = "Data" }
                Spacer(modifier = Modifier.width(8.dp))
                TabButton("Fiber", selectedTab) { selectedTab = "Fiber" }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        if (it.length <= PHONE_MAX_LENGTH) {
                            searchText = it
                            onSearchQueryChanged(it)
                        }
                    },
                    label = { Text("Phone Number") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Phone
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        onSearchQueryChanged(searchText)
                    }),
                    trailingIcon = {
                        IconButton(onClick = {
                            Toast.makeText(context, "Not Handled yet!", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Person Icon"
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(3f, true)
                        .align(Alignment.CenterVertically)
                        .testTag("phone_number")
                        .semantics {
                            testTagsAsResourceId = true
                        }
                )

                Button(
                    onClick = {
                        statusUpdate.rechargeModel?.let {
                            onNextClick(it.toJsonString())
                        } ?: run {
                            onActionStateChanged(ActionState.ACTION)
                        }
                    },
                    modifier = Modifier
                        .requiredWidth(70.dp)
                        .requiredHeight(55.dp)
                        .padding(end = 8.dp)
                        .weight(1f, false)
                        .align(Alignment.CenterVertically)
                        .testTag("next")
                        .semantics {
                            testTagsAsResourceId = true
                        }
                    ,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8BC34A) ,
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Default.Check,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "Next")

                }

            }
            statusUpdate.text?.let {
                Text(
                    text = it,
                    color = statusUpdate.color,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))



            when (actionState) {
                ActionState.NONE -> {
                    InfoDialog(
                        visibility = false,
                        onConfirm =  {}
                    )
                }

                ActionState.ACTION -> {
                    InfoDialog(
                        visibility = true,
                        onConfirm =  {onActionStateChanged(ActionState.NONE)},
                        info = statusUpdate.text
                    )
                }
            }
        }
    }
}

@Composable
fun TabButton(label: String, selectedTab: String, onClick: () -> Unit) {
    val isSelected = selectedTab == label
    Row {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSelected) Color(0xFF8BC34A) else Color(0xFFE0E0E0),
                contentColor = if (isSelected) Color.White else Color.Black
            ),
            modifier = Modifier
                .weight(1f, false)
                .height(40.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = label, fontSize = 14.sp)
        }
    }
}
