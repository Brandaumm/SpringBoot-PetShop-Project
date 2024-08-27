package org.example.springbootpetshopproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE Carrinho SET deletado_Em = CURRENT_TIMESTAMP WHERE id=?")
@SQLRestriction("deletado_Em is null")
public class Carrinho extends ModelGenerico {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "carrinho_produto",
            joinColumns = @JoinColumn(name = "id_carrinho"),
            inverseJoinColumns = @JoinColumn(name = "id_produto")
    )
    private List<Produto> produtos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // Métodos auxiliares para manter o mapeamento entre Usuario e Carrinho
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }
}


