package org.example.springbootpetshopproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.springbootpetshopproject.dto.UsuarioRequestDTO;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE Usuario SET deletado_Em = CURRENT_TIMESTAMP WHERE id=?")
@SQLRestriction("deletado_Em is null")

public class Usuario extends ModelGenerico implements UserDetails {

    @NotNull(message = "O nome não pode ser nulo.")
    @NotEmpty(message = "O nome precisa ser preenchido.")
    @Length(min = 2, max = 60, message = "O nome precisa conter mais do que {min} caracteres e menos do que {max} caracteres.")
    String nome;

    @Email
    @NotEmpty(message = "O email precisa ser preenchido.")
    @NotNull(message = "O email não pode ser nulo.")
    String email;

    @NotNull(message = "A senha não pode ser nula.")
    @NotEmpty(message = "A senha precisa ser preenchida.")
    String senha;

    @NotNull(message = "O contato não pode ser nulo.")
    @NotEmpty(message = "O contato precisa ser preenchido.")
    @NotBlank(message = "O contato não possuir espaços em branco.")
    @Length(max = 14, message = "O contato tem no máximo {max} caracteres.")
    String contato;

    @NotNull(message = "O endereço não pode ser nulo.")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_endereco")
    Endereco endereco;

    UserRole role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Carrinho> carrinhos = new ArrayList<>();


    public void atualizarDados (UsuarioRequestDTO dados){
        if(dados.getNome() != null){
            this.nome = dados.getNome();
        }
        if(dados.getEmail() != null){
            this.email = dados.getEmail();
        }
        if(dados.getSenha() != null){
            this.senha = new BCryptPasswordEncoder().encode(dados.getSenha());
        }
        if(dados.getContato() != null){
            this.contato = dados.getContato();
        }
        if(dados.getEndereco() != null){
            this.endereco = new Endereco(dados.getEndereco());
        }
    }

    public Usuario(String nome, String email, String encryptedPassword, String contato, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.senha = encryptedPassword;
        this.contato = contato;
        this.role = role;
        this.endereco = endereco;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
