package org.example.springbootpetshopproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponseDTO {
    String nome;
    int quantidade;
    Float preco;
    Boolean estoque = false;
    String descricao ;
}
