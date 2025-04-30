package br.com.miqueias.recruta_auth.dto.v1.usuario;

import java.io.Serializable;

public class UsuarioUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String lastname;

    public UsuarioUpdateRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}

