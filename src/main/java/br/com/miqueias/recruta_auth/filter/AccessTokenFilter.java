package br.com.miqueias.recruta_auth.filter;

import br.com.miqueias.recruta_auth.security.JWTService;
import br.com.miqueias.recruta_auth.security.UsuarioDetalheServiceImpl;
import br.com.miqueias.recruta_auth.utils.JWTUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessTokenFilter extends GenericFilterBean {

    private final JWTService jwtService;
    private final JWTUtils jwtUtils;
    private final UsuarioDetalheServiceImpl usuarioDetalheService;

    public AccessTokenFilter(JWTService jwtService, JWTUtils jwtUtils, UsuarioDetalheServiceImpl usuarioDetalheService) {
        this.jwtService = jwtService;
        this.jwtUtils = jwtUtils;
        this.usuarioDetalheService = usuarioDetalheService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = jwtUtils.getTokenFromRequest(httpRequest);

        if (token != null && !token.trim().isEmpty()) {
            try {
                JWTClaimsSet claims = jwtService.validateToken(token);
                String tokenType = claims.getStringClaim("type_token");

                if ("ACCESS".equals(tokenType)) {
                    String username = claims.getSubject();
                    UserDetails userDetails = usuarioDetalheService.loadUserById(Long.valueOf(username));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Token inválido: " + e.getMessage());
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
