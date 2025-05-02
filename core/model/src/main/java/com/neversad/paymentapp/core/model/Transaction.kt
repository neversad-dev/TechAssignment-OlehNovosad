package com.neversad.paymentapp.core.model

data class Transaction(
    val id: String,
    val status: Status,
    val purchaseAmount: String,
    val taxableAmount: String,
    val taxRate: String,
    val tipAmount: String,
    val discountAmount: String,
    val timestamp: String,
) {
    enum class Status {
        SUCCESS,
        FAILED,
    }

    companion object {
        val Empty =
            Transaction(
                id = "",
                status = Status.FAILED,
                purchaseAmount = "0.00",
                taxableAmount = "0.00",
                taxRate = "0.00",
                tipAmount = "0.00",
                discountAmount = "0.00",
                timestamp = "",
            )
    }
}
