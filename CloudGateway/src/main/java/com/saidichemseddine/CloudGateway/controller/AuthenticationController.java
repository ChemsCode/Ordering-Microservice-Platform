package com.saidichemseddine.CloudGateway.controller;

import com.saidichemseddine.CloudGateway.model.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @GetMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (
            @AuthenticationPrincipal OidcUser user,
            Model model,
            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient authorizedClient
    ) {
        AuthenticationResponse response = AuthenticationResponse.builder()
                .userId(user.getEmail())
                .accessToken(authorizedClient.getAccessToken().getTokenValue())
                .refreshToken(authorizedClient.getRefreshToken().getTokenValue())
                .expiresAt(authorizedClient.getAccessToken().getExpiresAt().getEpochSecond())
                .authorityList(user.getAuthorities().stream().map(a -> a.getAuthority()).toList())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
