package com.nps.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class DepositResponse(
    val amount: Int,
    val status: String,
    @JsonProperty("status_reason")
    val statusReason: String?
)
