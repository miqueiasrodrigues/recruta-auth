package br.com.miqueias.recruta_auth.dto.v1.usuario;

import java.io.Serializable;

public class UsuarioChangePasswordRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String password;

    public UsuarioChangePasswordRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

