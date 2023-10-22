package io.prince.kotlinspringbackend.config

import io.prince.kotlinspringbackend.config.authentication.filter.JwtAuthenticationFilter
import io.prince.kotlinspringbackend.config.authentication.provider.GoogleOAuthAuthenticationProvider
import io.prince.kotlinspringbackend.config.service.CustomUserDetailsService
import io.prince.kotlinspringbackend.config.service.GoogleOidcUserService
import io.prince.kotlinspringbackend.config.service.JwtService
import io.prince.kotlinspringbackend.config.success.handler.GoogleOAuthAuthenticationSuccessHandler
import io.prince.kotlinspringbackend.domain.repository.RoleRepository
import io.prince.kotlinspringbackend.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Value("\${domain}")
    private val domain: String,
    @Value("\${frontend.url}")
    private val frontEndUrl: String,
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val customUserDetailsService: CustomUserDetailsService,
    private val googleOAuthAuthenticationProvider: GoogleOAuthAuthenticationProvider
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            cors {  }
            authorizeHttpRequests {
//                authorize(anyRequest, authenticated)
                authorize(anyRequest, permitAll)
            }
            oauth2Login {
                userInfoEndpoint {
                    oidcUserService = oidcUserService()
                }
                authenticationSuccessHandler = googleOAuthAuthenticationSuccessHandler()
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(
                JwtAuthenticationFilter(
                    jwtService,
                    customUserDetailsService
                )
            )
        }
        return http.build()
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration().applyPermitDefaultValues()
        config.allowedOrigins = listOf("*")
        config.allowedMethods = listOf("*")
        config.allowedHeaders = listOf("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Bean
    fun googleOAuthAuthenticationSuccessHandler(): GoogleOAuthAuthenticationSuccessHandler {
        return GoogleOAuthAuthenticationSuccessHandler(
            domain,
            frontEndUrl,
            googleOAuthAuthenticationManager(googleOAuthAuthenticationProvider),
            customUserDetailsService,
            userRepository,
            roleRepository,
            jwtService
        )
    }

    @Bean
    fun googleOAuthAuthenticationManager(googleOAuthAuthenticationProvider: GoogleOAuthAuthenticationProvider): AuthenticationManager {
        return ProviderManager(listOf(googleOAuthAuthenticationProvider))
    }

    @Bean
    fun oidcUserService(): OidcUserService {
        return GoogleOidcUserService(OidcUserService())
    }
}