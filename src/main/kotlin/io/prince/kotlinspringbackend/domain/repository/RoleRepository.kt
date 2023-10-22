package io.prince.kotlinspringbackend.domain.repository

import io.prince.kotlinspringbackend.domain.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository: JpaRepository<Role, String> {
    fun findByName(name: String): Optional<Role>
}