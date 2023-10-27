package com.example.invest.EntityRes

import java.io.Serializable

data class ProjTotalBudget(
    val items: List<ProjTotalBudgetItem>,
    val page: Int,
    val perPage: Int,
    val totalItems: Int,
    val totalPages: Int
):Serializable
data class ProjTotalBudgetItem(
    val budget: Int,
    val forProject: String
):Serializable