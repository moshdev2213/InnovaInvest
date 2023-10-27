package com.example.innova.EntityRes

import java.io.Serializable

data class UserRecord(
    val token: String,
    val record: Record
):Serializable

data class Record(
    val id: String,
    val collectionId: String,
    val collectionName: String,
    val username: String,
    val verified: Boolean,
    val emailVisibility: Boolean,
    val email: String,
    val description: String,
    val created: String,
    val updated: String,
    val name: String,
    val avatar: String,
    val role: String,
    val telephone: String
):Serializable
