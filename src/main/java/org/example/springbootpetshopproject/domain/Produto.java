package org.example.springbootpetshopproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.springbootpetshopproject.dto.ProdutoAtualizarDTO;
import org.example.springbootpetshopproject.dto.ProdutoCadastroDTO;
import org.example.springbootpetshopproject.dto.ProdutoRequestDTO;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE Produto SET deletado_Em = CURRENT_TIMESTAMP WHERE id=?")
@SQLRestriction("deletado_Em is null")
public class Produto extends ModelGenerico {

    @NotEmpty(message = "O nome do produto precisa ser preenchido.")
    String nome;

    @DecimalMin(value = "0", message = "A quantidade do produto precisa ser maior que 0")
    int quantidade;

    @DecimalMin(value = "0", message = "O valor do produto ser maior que 0.")
    Float preco;

    Boolean estoque = false;

    @Length(min = 2, max = 100, message = "A descrição do produto precisa conter mais do que {min} caracteres e menos do que {max} caracteres.")
    String descricao ;

    @ManyToMany(mappedBy = "produtos")
    private Set<Carrinho> carrinhos = new HashSet<>();

    public Produto(ProdutoCadastroDTO produto) {
        this.nome = produto.nome();
        this.descricao = produto.descricao();
        this.quantidade = produto.quantidade();
        this.preco = produto.preco();
    }

    public void atualizar(ProdutoAtualizarDTO produto) {
        if(produto.nome() != null){
            this.nome = produto.nome();
            System.out.println("ATUALIZOUUUUU");
        }
        if(produto.descricao() != null){
            this.descricao = produto.descricao();
        }
        if(produto.estoque() != this.estoque){
            this.estoque  = produto.estoque();
        }
        if(produto.quantidade() != this.quantidade){
            this.quantidade  = produto.quantidade();
        }
        if(produto.preco() != this.preco){
            this.preco = produto.preco();
        }
    }
}