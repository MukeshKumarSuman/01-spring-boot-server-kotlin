package com.nps.security

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException
import org.springframework.stereotype.Component
import java.nio.charset.Charset
import java.util.*

@Component
class AppApiAuthenticationManger(@Value("\${app.auth.bearer-token}") val authToken: String) : AuthenticationManager {

    val logger = LoggerFactory.getLogger(AppApiAuthenticationManger::class.java)

    override fun authenticate(authentication: Authentication?): Authentication {
        if (authentication == null) {
            logger.error("Unauthorized login attempt! Bearer token missing.")
            throw PreAuthenticatedCredentialsNotFoundException("Authentication is not presented")
        }

        val token: String = decodeToken(authentication.principal as String)
        if (authToken == token) {
            return UsernamePasswordAuthenticationToken(token, null, null)
        }
        throw BadCredentialsException("Invalid token provided")
    }

    private fun decodeToken(token: String): String {
        try {
            return String(Base64.getDecoder().decode(token), Charset.defaultCharset())
        }catch (e: Exception) {
            throw BadCredentialsException("Can not decode token", e)
        }
    }
}