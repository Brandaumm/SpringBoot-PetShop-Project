package org.example.springbootpetshopproject.controller;


import lombok.AllArgsConstructor;
import org.example.springbootpetshopproject.domain.Carrinho;
import org.example.springbootpetshopproject.dto.CarrinhoRequestDTO;
import org.example.springbootpetshopproject.dto.CarrinhoResponseDTO;
import org.example.springbootpetshopproject.service.CarrinhoService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/carrinhos/")
public class CarrinhoController {

    private final CarrinhoService service;
    private final ModelMapper mapper;


    @PostMapping
    public ResponseEntity<CarrinhoResponseDTO> cadastrarCarrinho(@RequestBody CarrinhoRequestDTO dto) {
        Carrinho carrinho = converterParaEntidade(dto);
        Carrinho carrinhoSalvo = service.cadastrarCarrinho(carrinho);
        return ResponseEntity.ok(converterParaDTO(carrinhoSalvo));
    }

    @PutMapping("{id}")
    public ResponseEntity<CarrinhoResponseDTO> atualizarCarrinho(@PathVariable Long id, @RequestBody CarrinhoRequestDTO dto) {
        Carrinho carrinho = converterParaEntidade(dto);
        Carrinho carrinhoAtualizado = service.atualizarCarrinho(id, carrinho);
        return ResponseEntity.ok(converterParaDTO(carrinhoAtualizado));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("id") Long id) {
        service.deletarPorId(id);
    }

    @PostMapping("{carrinhoId}/produtos/{produtoId}")
    public ResponseEntity<CarrinhoResponseDTO> adicionarProduto(@PathVariable Long carrinhoId, @PathVariable Long produtoId) {
        Carrinho carrinhoAtualizado = service.adicionarProduto(carrinhoId, produtoId);
        return ResponseEntity.ok(converterParaDTO(carrinhoAtualizado));
    }

    @DeleteMapping("{carrinhoId}/produtos/{produtoId}")
    public ResponseEntity<CarrinhoResponseDTO> removerProduto(@PathVariable Long carrinhoId, @PathVariable Long produtoId) {
        Carrinho carrinhoAtualizado = service.removerProduto(carrinhoId, produtoId);
        return ResponseEntity.ok(converterParaDTO(carrinhoAtualizado));
    }


    @GetMapping
    public Page<CarrinhoResponseDTO> listarTodos(Pageable pageable) {
        Page<Carrinho> carrinhos = service.listarTodos(pageable);

        return carrinhos.map(this::converterParaDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<CarrinhoResponseDTO> listarPorId(@PathVariable("id") Long id) {
        Carrinho carrinhoEntidade = service.listarPorId(id);
        CarrinhoResponseDTO carrinhoDTO = mapper.map(carrinhoEntidade, CarrinhoResponseDTO.class);

        return ResponseEntity.ok(carrinhoDTO);
    }


    // METODOS CONVERSORES DE ENTIDADE E DTO
    private Carrinho converterParaEntidade(CarrinhoRequestDTO dto) {
        return mapper.map(dto, Carrinho.class);
    }

    private CarrinhoResponseDTO converterParaDTO(Carrinho carrinho) {
        return mapper.map(carrinho, CarrinhoResponseDTO.class);
    }
}
