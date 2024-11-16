package com.nps.service

import com.nps.dto.request.DepositRequest
import com.nps.dto.response.DepositResponse
import com.nps.exception.AccountNotFoundException
import com.nps.exception.InsufficientBalanceException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PartnerService() {
    val logger = LoggerFactory.getLogger(PartnerService::class.java)
    fun deposit(depositRequest: DepositRequest): DepositResponse {
        logger.info("Request: {}", depositRequest)
        val depositResponse = DepositResponse(1000, "accepted")
        logger.info("Request: {}", depositResponse)
        if (depositRequest.amount < 10) {
            throw InsufficientBalanceException("Insufficient balance")
        }
        if (depositRequest.amount > 1000) {
            throw AccountNotFoundException("Account not found")
        }
        return depositResponse
    }
}