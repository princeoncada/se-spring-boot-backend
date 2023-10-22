package io.prince.kotlinspringbackend.config.authentication.provider

import io.prince.kotlinspringbackend.config.service.CustomUserDetails
import io.prince.kotlinspringbackend.domain.repository.UserRepository
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Service

@Service
class GoogleOAuthAuthenticationProvider(
    private val userRepository: UserRepository
): AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        if (authentication is OAuth2AuthenticationToken) {
            val userDetails = extractUserDetails(authentication)
            return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        }
        throw UsernameNotFoundException("Authentication Provider: Google OAuth authentication failed.")
    }

    override fun supports(authentication: Class<*>): Boolean {
        return OAuth2AuthenticationToken::class.java.isAssignableFrom(authentication)
    }

    private fun extractUserDetails(authentication: OAuth2AuthenticationToken): CustomUserDetails {
        val email = authentication.principal.attributes["email"] as String
        val user = userRepository.findByEmail(email)
        if (user.isEmpty) throw UsernameNotFoundException("Authentication Provider / User Details Extraction: User not found with email: $email")
        return CustomUserDetails(user.get())
    }
}