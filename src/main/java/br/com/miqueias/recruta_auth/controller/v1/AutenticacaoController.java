package br.com.miqueias.recruta_auth.controller.v1;

import br.com.miqueias.recruta_auth.dto.v1.token.AccessTokenResponse;
import br.com.miqueias.recruta_auth.dto.v1.token.RefreshTokenRequest;
import br.com.miqueias.recruta_auth.dto.v1.token.TokenResponse;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioLoginRequest;
import br.com.miqueias.recruta_auth.service.v1.AutenticacaoService;
import br.com.miqueias.recruta_auth.service.v1.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    @Autowired
    public AutenticacaoController(AutenticacaoService autenticacaoService, UsuarioService usuarioService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody UsuarioLoginRequest usuarioLoginRequest) {
        TokenResponse tokenResponse = autenticacaoService.authenticate(usuarioLoginRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AccessTokenResponse accessTokenResponse = autenticacaoService.refresh(refreshTokenRequest);
        return ResponseEntity.ok(accessTokenResponse);
    }

}
