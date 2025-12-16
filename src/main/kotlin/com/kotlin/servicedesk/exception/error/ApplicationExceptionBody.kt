package com.kotlin.servicedesk.exception.error

import org.springframework.http.HttpStatus

class ApplicationExceptionBody(message: String, val status: HttpStatus) : RuntimeException(message){
}