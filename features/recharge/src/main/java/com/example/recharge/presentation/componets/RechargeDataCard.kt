package com.example.recharge.presentation.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharedData.model.RechargeModel


@Composable
fun RechargeDataCard(
    rechargeModel: RechargeModel
){
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp), // Set elevation here
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = rechargeModel.phoneNum, fontSize = 18.sp)
            Text(
                text = rechargeModel.rechargeType ?: "-",
                fontSize = 16.sp,
            )

        }

        }
}