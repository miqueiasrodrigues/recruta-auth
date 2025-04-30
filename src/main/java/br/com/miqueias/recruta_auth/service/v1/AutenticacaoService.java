package br.com.miqueias.recruta_auth.service.v1;

import br.com.miqueias.recruta_auth.dto.v1.token.AccessTokenResponse;
import br.com.miqueias.recruta_auth.dto.v1.token.RefreshTokenRequest;
import br.com.miqueias.recruta_auth.dto.v1.token.TokenResponse;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioLoginRequest;

public interface AutenticacaoService {
    TokenResponse authenticate(UsuarioLoginRequest usuarioLoginRequest);
    AccessTokenResponse refresh(RefreshTokenRequest refreshTokenRequest);
}
