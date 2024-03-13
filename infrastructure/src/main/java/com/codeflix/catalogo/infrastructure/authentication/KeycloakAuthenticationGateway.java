package com.codeflix.catalogo.infrastructure.authentication;

import com.codeflix.catalogo.domain.exceptions.InternalErrorException;
import com.codeflix.catalogo.infrastructure.configuration.annotations.Keycloak;
import com.codeflix.catalogo.infrastructure.configuration.properties.KeycloakProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Component
public class KeycloakAuthenticationGateway implements AuthenticationGateway {

    private final RestClient restClient;
    private final String tokenUri;

    public KeycloakAuthenticationGateway(
            @Keycloak final RestClient restClient,
            final KeycloakProperties keycloakProperties
    ) {
        this.restClient = Objects.requireNonNull(restClient);
        this.tokenUri = keycloakProperties.tokenUri();
    }

    @Override
    public AuthenticationResult login(final ClientCredentialsInput input) {
        final var map = new LinkedMultiValueMap<>();
        map.set("grant_type", "client_credentials");
        map.set("client_id", input.clientId());
        map.set("client_secret", input.clientSecret());

        final var output = this.restClient.post()
                .uri(tokenUri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .body(KeycloakAuthenticationResult.class);

        if (output == null) {
            throw InternalErrorException.with("Failed to create client credentials [clientId:%s]".formatted(input.clientId()));
        }

        return new AuthenticationResult(output.accessToken, output.refreshToken);
    }

    @Override
    public AuthenticationResult refresh(final RefreshTokenInput input) {
        final var map = new LinkedMultiValueMap<>();
        map.set("grant_type", "refresh_token");
        map.set("client_id", input.clientId());
        map.set("client_secret", input.clientSecret());
        map.set("refresh_token", input.refreshToken());

        final var output = this.restClient.post()
                .uri(tokenUri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .body(KeycloakAuthenticationResult.class);

        if (output == null) {
            throw InternalErrorException.with("Failed to refresh client credentials [clientId:%s]".formatted(input.clientId()));
        }

        return new AuthenticationResult(output.accessToken, output.refreshToken);
    }

    public record KeycloakAuthenticationResult(
            String accessToken,
            String refreshToken
    ) {
    }
}
