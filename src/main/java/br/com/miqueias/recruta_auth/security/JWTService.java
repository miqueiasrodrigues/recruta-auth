package br.com.miqueias.recruta_auth.security;

import br.com.miqueias.recruta_auth.config.properties.RSAProperties;
import br.com.miqueias.recruta_auth.config.properties.TokenProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

@Service
public class JWTService {

    private final RSAProperties rsaProperties;
    private final TokenProperties tokenProperties;

    public JWTService(RSAProperties rsaProperties, TokenProperties tokenProperties) {
        this.rsaProperties = rsaProperties;
        this.tokenProperties = tokenProperties;
    }

    public String generateAccessToken(UsuarioDetalhe usuarioDetalhe) {
        Instant now = Instant.now();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(usuarioDetalhe.getId().toString())
                .claim("roles", usuarioDetalhe.getAuthorities())
                .claim("type_token", "ACCESS")
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(tokenProperties.getExpiration())))
                .build();

        return signToken(claims);
    }

    public String generateRefreshToken(UsuarioDetalhe usuarioDetalhe) {
        Instant now = Instant.now();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(usuarioDetalhe.getId().toString())
                .claim("type_token", "REFRESH")
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(tokenProperties.getRefreshExpiration())))
                .build();

        return signToken(claims);
    }

    private String signToken(JWTClaimsSet claims) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.RS256);
        SignedJWT jwt = new SignedJWT(header, claims);

        try {
            jwt.sign(new RSASSASigner(rsaProperties.getPrivateKey()));
        } catch (JOSEException e) {
            throw new RuntimeException("Não foi possível gerar o token", e);
        }

        return jwt.serialize();
    }

    public JWTClaimsSet validateToken(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);

            JWSVerifier verifier = new RSASSAVerifier(rsaProperties.getPublicKey());

            if (!jwt.verify(verifier)) {
                throw new RuntimeException("Assinatura do token inválida");
            }

            JWTClaimsSet claims = jwt.getJWTClaimsSet();

            if (claims.getExpirationTime() != null && claims.getExpirationTime().before(new Date())) {
                throw new RuntimeException("Token expirado");
            }

            return claims;

        } catch (ParseException e) {
            throw new RuntimeException("Erro ao analisar o token", e);
        } catch (JOSEException e) {
            throw new RuntimeException("Erro ao verificar a assinatura do token", e);
        }
    }
}
