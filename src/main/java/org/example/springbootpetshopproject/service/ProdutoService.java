package org.example.springbootpetshopproject.service;

import org.example.springbootpetshopproject.domain.Produto;
import org.example.springbootpetshopproject.repository.IProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class ProdutoService extends GenericoService<Produto, IProdutoRepository> {

    public ProdutoService(IProdutoRepository repository) {
        super(repository);
    }


    public Produto cadastrarProduto(Produto produto) {
        return repository.save(produto);
    }


    public void deletarProduto(Long id) {
        Optional<Produto> produtoOpt = repository.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            produto.setDeletadoEm(LocalDateTime.now());
            repository.save(produto);
        } else {
            throw new IllegalArgumentException("Produto não encontrado.");
        }
    }


    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        if (repository.existsById(id)) {
            produtoAtualizado.setId(id);
            return repository.save(produtoAtualizado);
        } else {
            throw new IllegalArgumentException("Produto não encontrado.");
        }
    }
}