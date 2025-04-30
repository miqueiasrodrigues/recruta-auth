package br.com.miqueias.recruta_auth.core;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ExceptionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mensagem;
    private String detalhe;


    public ExceptionResponse(String mensagem, String detalhe) {
        this.mensagem = mensagem;
        this.detalhe = detalhe;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }
}
