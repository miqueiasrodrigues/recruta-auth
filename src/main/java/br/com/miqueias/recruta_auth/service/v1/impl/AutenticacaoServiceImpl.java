package br.com.miqueias.recruta_auth.service.v1.impl;

import br.com.miqueias.recruta_auth.dto.v1.token.AccessTokenResponse;
import br.com.miqueias.recruta_auth.dto.v1.token.RefreshTokenRequest;
import br.com.miqueias.recruta_auth.dto.v1.token.TokenResponse;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioLoginRequest;
import br.com.miqueias.recruta_auth.exception.UnauthorizedException;
import br.com.miqueias.recruta_auth.mapper.v1.UsuarioMapper;
import br.com.miqueias.recruta_auth.model.Usuario;
import br.com.miqueias.recruta_auth.security.JWTService;
import br.com.miqueias.recruta_auth.security.UsuarioDetalhe;
import br.com.miqueias.recruta_auth.security.UsuarioDetalheServiceImpl;
import br.com.miqueias.recruta_auth.service.v1.AutenticacaoService;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {
    private final AuthenticationManager authenticationManager;
    private final UsuarioDetalheServiceImpl usuarioDetalheService;
    private final JWTService jwtService;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AutenticacaoServiceImpl(AuthenticationManager authenticationManager, UsuarioDetalheServiceImpl usuarioDetalheService, JWTService jwtService, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.usuarioDetalheService = usuarioDetalheService;
        this.jwtService = jwtService;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public TokenResponse authenticate(UsuarioLoginRequest usuarioLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioLoginRequest.getCpf(), usuarioLoginRequest.getPassword())
        );

        UsuarioDetalhe usuarioDetalhe = (UsuarioDetalhe) authentication.getPrincipal();

        String accessToken =  jwtService.generateAccessToken(usuarioDetalhe);
        String refreshToken = jwtService.generateRefreshToken(usuarioDetalhe);

        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public AccessTokenResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        JWTClaimsSet claims = jwtService.validateToken(refreshTokenRequest.getRefreshToken());

        String typeToken = null;
        try {
            typeToken = claims.getStringClaim("type_token");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (!"REFRESH".equals(typeToken)) {
            throw new RuntimeException("Token não é um refresh token válido");
        }

        String usuarioId = claims.getSubject();

        UsuarioDetalhe usuarioDetalhe = usuarioDetalheService.loadUserById(Long.parseLong(usuarioId));
        String newAccessToken = jwtService.generateAccessToken(usuarioDetalhe);

        return new AccessTokenResponse(newAccessToken);
    }

}
