package io.prince.kotlinspringbackend.config.authentication.filter

import io.jsonwebtoken.Claims
import io.prince.kotlinspringbackend.config.service.CustomUserDetails
import io.prince.kotlinspringbackend.config.service.CustomUserDetailsService
import io.prince.kotlinspringbackend.config.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.JwtValidationException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authorizationHeader: String? = request.getHeader("Authorization")
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response)
                return
            }

            val jwtToken = authorizationHeader.substring(7)
            val payload: Claims = jwtService.getClaims(jwtToken)
            val userDetails: CustomUserDetails = customUserDetailsService.loadUserByUsername(payload.subject)

            if (!jwtService.isTokenValid(jwtToken, userDetails)) throw JwtValidationException("Invalid token", null)

            val roles = payload["authorities"] as List<*>
            val authorities = roles.map { SimpleGrantedAuthority(it as String) }
            val authenticationToken = UsernamePasswordAuthenticationToken(
                userDetails.username,
                null,
                authorities
            )

            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authenticationToken

            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            response.sendError(401, "Invalid token")
        }
    }
}