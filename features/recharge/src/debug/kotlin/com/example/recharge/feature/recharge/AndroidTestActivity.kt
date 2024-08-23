package com.example.recharge.feature.recharge

import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint


/**
 * AndroidTestActivity is an activity used for Android instrumented tests.
 * It is annotated with `@AndroidEntryPoint` to enable Hilt dependency injection,
 * allowing the activity to receive dependencies injected by Hilt.
 */

@AndroidEntryPoint
class AndroidTestActivity : ComponentActivity()