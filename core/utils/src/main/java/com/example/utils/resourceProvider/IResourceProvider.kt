package com.example.utils.resourceProvider

import androidx.annotation.StringRes

interface IResourceProvider {

  fun getText(@StringRes resId: Int, vararg values: Any): String

}