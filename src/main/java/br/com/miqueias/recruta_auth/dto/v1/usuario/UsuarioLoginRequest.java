package br.com.miqueias.recruta_auth.dto.v1.usuario;

import br.com.miqueias.recruta_auth.utils.StringUtils;

import java.io.Serializable;

public class UsuarioLoginRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cpf;
    private String password;

    public UsuarioLoginRequest() {

    }

    public String getCpf() {
        return  cpf != null ? StringUtils.clean(cpf) : null;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf != null ? StringUtils.clean(cpf) : null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
