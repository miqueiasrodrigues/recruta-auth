package br.com.miqueias.recruta_auth.security;

import br.com.miqueias.recruta_auth.model.Papel;
import br.com.miqueias.recruta_auth.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioDetalhe implements UserDetails {
    private final Usuario usuario;

    public UsuarioDetalhe(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Papel> papeis = usuario.getPapeis();
        return papeis.stream()
                .map(papel -> new SimpleGrantedAuthority("ROLE_" + papel.getDescricao()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getCpf();
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
        return usuario.isAtivo();
    }

    public Long getId() {
        return usuario.getId();
    }
}
