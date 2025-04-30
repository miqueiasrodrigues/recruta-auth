package br.com.miqueias.recruta_auth.dto.v1.token;

import java.io.Serializable;

public class RefreshTokenRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String refreshToken;

    public RefreshTokenRequest() {
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
