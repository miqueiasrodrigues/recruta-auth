package br.com.miqueias.recruta_auth.dto.v1.token;

import java.io.Serializable;

public class AccessTokenResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String accessToken;

    public AccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
