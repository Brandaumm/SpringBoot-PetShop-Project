package org.example.springbootpetshopproject.repository;

import org.example.springbootpetshopproject.domain.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICarrinhoRepository extends JpaRepository<Carrinho, Long> {
}
