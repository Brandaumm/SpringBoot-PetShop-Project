package org.example.springbootpetshopproject.controller;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.springbootpetshopproject.domain.Produto;
import org.example.springbootpetshopproject.dto.*;
import org.example.springbootpetshopproject.repository.IProdutoRepository;
import org.example.springbootpetshopproject.service.ProdutoService;
import org.example.springbootpetshopproject.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/produtos/")
@CrossOrigin(origins = "http://localhost:42000")
public class ProdutoController {

    @Autowired
    ProdutoService service;

    private final ModelMapper mapper;

    @Autowired
    IProdutoRepository repository;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody ProdutoCadastroDTO produto) {
       var produtoEntidade = new Produto(produto);
       produtoEntidade.setEstoque(true);
       repository.save(produtoEntidade);
       return ResponseEntity.ok(converterParaDTO(produtoEntidade));

    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity atualizar (@RequestBody ProdutoAtualizarDTO produto,@PathVariable Long id) {
        var produtoAtualizar = repository.getReferenceById(id);
        produtoAtualizar.atualizar(produto);
        return ResponseEntity.ok(new ProdutoAtualizarDTO(produto));
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoListar>> ListAll(Pageable paginacao){
        var page = repository.findAllByDeletadoEmNull(paginacao);
        return ResponseEntity.ok(page);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deletar(@PathVariable("id") Long id){
        service.deletarPorId(id);
        return ResponseEntity.noContent().build();
    };


    private ProdutoResponseDTO converterParaDTO(Produto produtoEntidade) {
        ProdutoResponseDTO produtoDTO = mapper.map(produtoEntidade, ProdutoResponseDTO.class);
        return produtoDTO;
    }

    private Produto converterParaEntidade(ProdutoRequestDTO produtoDTO) {
        Produto produtoEntidade = mapper.map(produtoDTO, Produto.class);
        return produtoEntidade;
    }
}
