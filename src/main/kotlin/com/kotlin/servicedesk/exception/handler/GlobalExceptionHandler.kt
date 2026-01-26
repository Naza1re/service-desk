package com.kotlin.servicedesk.exception.handler

import com.kotlin.servicedesk.exception.FileNotFoundException
import com.kotlin.servicedesk.exception.error.ApplicationExceptionBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"
    }

    @ExceptionHandler(FileNotFoundException::class)
    fun handleNotFoundException(e: Exception): ResponseEntity<ApplicationExceptionBody> {
        val message = e.message ?: UNKNOWN_ERROR
        return ResponseEntity<ApplicationExceptionBody>(ApplicationExceptionBody(message, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND)
    }
}