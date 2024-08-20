package com.example.utils.core

import com.example.utils.core.Constant.zainRecognizekey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


inline fun <reified T> T.toJsonString(): String {
    val gson = Gson()
    return gson.toJson(this)
}

inline fun <reified T> String.jsonParse(): T {
    val typeToken = object : TypeToken<T>() {}.type
    val gson = Gson()
    return gson.fromJson(this, typeToken)
}


fun String.isZainNumber(): Boolean =
    zainRecognizekey.any {
        this.startsWith(it)
    }