package br.com.miqueias.recruta_auth.dto.v1.usuario;

import br.com.miqueias.recruta_auth.utils.StringUtils;

import java.io.Serializable;

public class UsuarioCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String lastname;
    private String cpf;
    private String email;
    private String password;

    public UsuarioCreateRequest() {
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

    public String getCpf() {
        return  cpf != null ? StringUtils.clean(cpf) : null;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf != null ? StringUtils.clean(cpf) : null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

