//package com.nps.security
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import jakarta.servlet.FilterChain
//import jakarta.servlet.ServletRequest
//import jakarta.servlet.ServletResponse
//import jakarta.servlet.http.HttpServletRequest
//import jakarta.servlet.http.HttpServletResponse
//import org.springframework.http.HttpHeaders
//import org.springframework.http.HttpStatus
//import org.springframework.security.authentication.InternalAuthenticationServiceException
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.web.filter.GenericFilterBean
//import java.io.IOException
//
//class AppApiAuthenticationFilter (
//    private val objectMapper: ObjectMapper,
//    private val authenticationManger: AppApiAuthenticationManger) : GenericFilterBean() {
//
//    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
//        val request = req as HttpServletRequest
//        val response = res as HttpServletResponse
//
//        try {
//            val token = request.getHeader(HttpHeaders.AUTHORIZATION)
//            val requestAuthentication = UsernamePasswordAuthenticationToken(token, null, null)
//            val responseAuthentication = authenticationManger.authenticate(requestAuthentication)
//            if (!responseAuthentication.isAuthenticated) {
//                throw InternalAuthenticationServiceException("Unable to authenticate with given token")
//            }
//            SecurityContextHolder.getContext().authentication = requestAuthentication
//            chain.doFilter(request, response)
//        } catch (e: Exception) {
//            logger.error("Unable to authenticate", e)
//            SecurityContextHolder.clearContext()
//            throwAuthFailedErrorResponse(response)
//        } finally {
//            SecurityContextHolder.clearContext()
//        }
//    }
//
//    private fun throwAuthFailedErrorResponse(response: HttpServletResponse) {
//        writeErrorResponse(response, "Authorization Required", HttpStatus.UNAUTHORIZED)
//    }
//
//    @Throws(IOException::class)
//    private fun writeErrorResponse(response: HttpServletResponse, errorResponse: String, status: HttpStatus) {
//        response.apply {
//            this.setHeader("Content-Type", "Application/json")
//            this.status = status.value()
//            this.outputStream.write(restResponseBytes(errorResponse))
//        }
//    }
//
//    @Throws(IOException::class)
//    private fun restResponseBytes(errorResponse: String): ByteArray {
//        return objectMapper.writeValueAsString(errorResponse).toByteArray()
//    }
//}