package org.example.springbootpetshopproject.repository;

import org.example.springbootpetshopproject.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthorizationRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);
}
