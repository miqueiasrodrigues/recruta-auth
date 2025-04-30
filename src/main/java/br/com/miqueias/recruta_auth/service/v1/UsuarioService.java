package br.com.miqueias.recruta_auth.service.v1;


import br.com.miqueias.recruta_auth.dto.v1.papel.PapelAssociateRequest;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioChangePasswordRequest;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioCreateRequest;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioResponse;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioUpdateRequest;

public interface UsuarioService {
    UsuarioResponse create(UsuarioCreateRequest usuarioCreateRequest);
    UsuarioResponse update(UsuarioUpdateRequest usuarioUpdateRequest);
    UsuarioResponse changePassword(UsuarioChangePasswordRequest usuarioChangePasswordRequest);
    UsuarioResponse associateRole(PapelAssociateRequest papelAssociateRequest);
}
