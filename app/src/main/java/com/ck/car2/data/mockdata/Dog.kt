package com.ck.car2.data.mockdata


import kotlinx.serialization.Serializable

@Serializable
data class Dog(
//    @SerialName(value = "message")
    val message: String,
    val status: String
)