package io.prince.kotlinspringbackend.config.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service

@Service
class GoogleOidcUserService (
    @Qualifier("oidcUserService")
    private val delegate: OidcUserService
): OidcUserService() {
    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val oidcUser = delegate.loadUser(userRequest)
        val idToken = oidcUser.idToken
        return DefaultOidcUser(oidcUser.authorities, idToken, "email")
    }
}