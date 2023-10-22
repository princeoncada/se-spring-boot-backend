package io.prince.kotlinspringbackend.config.service

import io.prince.kotlinspringbackend.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService (
    private val userRepository: UserRepository,
): UserDetailsService {
    override fun loadUserByUsername(email: String): CustomUserDetails {
        return try {
            val user = userRepository.findByEmail(email)
            CustomUserDetails(user.get())
        } catch (e: Exception) {
            throw UsernameNotFoundException("User not found with email: $email / This is inside UserDetailsService")
        }
    }
}