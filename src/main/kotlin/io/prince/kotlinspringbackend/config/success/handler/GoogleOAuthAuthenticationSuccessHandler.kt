package io.prince.kotlinspringbackend.config.success.handler

import io.prince.kotlinspringbackend.config.service.CustomUserDetailsService
import io.prince.kotlinspringbackend.config.service.JwtService
import io.prince.kotlinspringbackend.domain.model.User
import io.prince.kotlinspringbackend.domain.repository.RoleRepository
import io.prince.kotlinspringbackend.domain.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.time.Instant
import java.util.*

class GoogleOAuthAuthenticationSuccessHandler(
    @Value("\${domain}")
    private val domain: String,
    private val frontEndUrl: String,
    private val authenticationManager: AuthenticationManager,
    private val customUserDetailsService: CustomUserDetailsService,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val jwtService: JwtService,
): AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?,
    ) {
        try {
            authenticationManager.authenticate(authentication)
        } catch (e: Exception) {
            val newUser = User(
                id = UUID.randomUUID().toString(),
                role = roleRepository.findByName("USER").get(),
                email = authentication?.name!!,
                password = "",
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
            )
            userRepository.save(newUser)
            authenticationManager.authenticate(authentication)
        }

        val userDetails = customUserDetailsService.loadUserByUsername(authentication?.name!!)
        val token = jwtService.generateToken(userDetails)

        if (response != null) {
            val resCookie = ResponseCookie.from("jwtToken", token)
                .path("/")
                .maxAge(3600)
                .domain(domain)
                .build()

            response.addHeader("Set-Cookie", resCookie.toString())
            response.sendRedirect("${frontEndUrl}/authenticate")
        }
    }
}