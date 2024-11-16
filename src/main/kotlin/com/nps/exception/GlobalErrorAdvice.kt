package com.nps.exception

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalErrorAdvice {

    @ExceptionHandler(AccountNotFoundException::class)
    fun handleClientException(ex: AccountNotFoundException) = ErrorResponse(
        100, "ACCOUNT_NOT_FOUND", "Account not found"
    ).toResponseEntity(HttpStatus.BAD_REQUEST)

    @ExceptionHandler(InsufficientBalanceException::class)
    fun handleInsufficientException(ex: InsufficientBalanceException) = ErrorResponse(
        100, "VALIDATION_INSUFFICIENT_BALANCE", "Insufficient balance"
    ).toResponseEntity(HttpStatus.BAD_REQUEST)
}

@JsonPropertyOrder("type", "error", "timeStamp")
data class ErrorResponse (
    val code: Int,
    val errorConstant: String,
    val message: String? = null ) {
    fun toResponseEntity(status: HttpStatus): ResponseEntity<ErrorResponse> {
        return ResponseEntity(this, status)
    }
}