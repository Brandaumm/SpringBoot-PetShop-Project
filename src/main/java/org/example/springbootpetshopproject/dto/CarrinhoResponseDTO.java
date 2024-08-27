package org.example.springbootpetshopproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoResponseDTO {

    private Long id;

    private Long usuarioId;

    private List<ProdutoResponseDTO> produtos;
}