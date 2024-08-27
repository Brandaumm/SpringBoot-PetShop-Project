package org.example.springbootpetshopproject.service;

import org.example.springbootpetshopproject.domain.Carrinho;
import org.example.springbootpetshopproject.domain.Produto;
import org.example.springbootpetshopproject.repository.ICarrinhoRepository;
import org.example.springbootpetshopproject.repository.IProdutoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CarrinhoService extends GenericoService<Carrinho, ICarrinhoRepository> {

    private final IProdutoRepository produtoRepository;

    public CarrinhoService(ICarrinhoRepository repository, IProdutoRepository produtoRepository) {
        super(repository);
        this.produtoRepository = produtoRepository;
    }


    public Carrinho cadastrarCarrinho(Carrinho carrinho) {
        return repository.save(carrinho);
    }


    public void deletarCarrinho(Long id) {
        Optional<Carrinho> carrinhoOpt = repository.findById(id);
        if (carrinhoOpt.isPresent()) {
            Carrinho carrinho = carrinhoOpt.get();
            carrinho.setDeletadoEm(LocalDateTime.now());
            repository.save(carrinho);  //
        } else {
            throw new IllegalArgumentException("Carrinho não encontrado.");
        }
    }


    public Carrinho atualizarCarrinho(Long id, Carrinho carrinhoAtualizado) {
        if (repository.existsById(id)) {
            carrinhoAtualizado.setId(id);
            return repository.save(carrinhoAtualizado);
        } else {
            throw new IllegalArgumentException("Carrinho não encontrado.");
        }
    }


    public Carrinho adicionarProduto(Long carrinhoId, Long produtoId) {
        Optional<Carrinho> carrinhoOpt = repository.findById(carrinhoId);
        Optional<Produto> produtoOpt = produtoRepository.findById(produtoId);

        if (carrinhoOpt.isPresent() && produtoOpt.isPresent()) {
            Carrinho carrinho = carrinhoOpt.get();
            Produto produto = produtoOpt.get();
            carrinho.getProdutos().add(produto);
            return repository.save(carrinho);
        } else {
            throw new IllegalArgumentException("Carrinho ou Produto não encontrado.");
        }
    }


    public Carrinho removerProduto(Long carrinhoId, Long produtoId) {
        Optional<Carrinho> carrinhoOpt = repository.findById(carrinhoId);
        Optional<Produto> produtoOpt = produtoRepository.findById(produtoId);

        if (carrinhoOpt.isPresent() && produtoOpt.isPresent()) {
            Carrinho carrinho = carrinhoOpt.get();
            Produto produto = produtoOpt.get();
            carrinho.getProdutos().remove(produto);
            return repository.save(carrinho);
        } else {
            throw new IllegalArgumentException("Carrinho ou Produto não encontrado.");
        }
    }
}
