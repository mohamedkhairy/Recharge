package com.example.utils.resourceProvider

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProvider @Inject constructor(
  @ApplicationContext private val context: Context
) : IResourceProvider {

  override fun getText(@StringRes resId: Int, vararg values: Any) = context.getString(resId, *values)
}