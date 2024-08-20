package com.example.utils.mapper


interface BaseMapper<in FirstModel, out SecondModel> {
    fun map(model: FirstModel): SecondModel
}