package br.com.miqueias.recruta_auth.dto.v1.papel;

import java.io.Serializable;

public class PapelAssociateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long papelId;
    private final Long usuarioId;

    public PapelAssociateRequest(Long papelId, Long usuarioId) {
        this.papelId = papelId;
        this.usuarioId = usuarioId;
    }

    public Long getPapelId() {
        return papelId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }
}
