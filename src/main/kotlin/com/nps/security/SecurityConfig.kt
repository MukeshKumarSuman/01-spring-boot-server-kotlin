package com.nps.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity, authManger: AppApiAuthenticationManger): SecurityFilterChain =with(http) {
        csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests().anyRequest().authenticated()
            .and().oauth2ResourceServer()
            .opaqueToken().authenticationManager(authManger)
        return build()

    }
}

curl --location 'http://localhost:8082/deposit' \
--header 'Authorization: Bearer YTVlZmQ0YWEtNjgwNC00MzA5LWExYTMtZGE3ODFiZTlmYjc0' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=DA8EFCB6BFA070EF66E759C980E5D4A7' \
--data '{
"amount": 1000
}'