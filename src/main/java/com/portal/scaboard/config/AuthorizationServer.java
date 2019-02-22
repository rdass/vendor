package com.portal.scaboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
	static final String CLIEN_ID = "client"; //$NON-NLS-1$
	static final String CLIENT_SECRET = "secret"; //$NON-NLS-1$
	static final String GRANT_TYPE_PASSWORD = "password"; //$NON-NLS-1$
	static final String AUTHORIZATION_CODE = "authorization_code"; //$NON-NLS-1$
	static final String REFRESH_TOKEN = "refresh_token"; //$NON-NLS-1$
	static final String IMPLICIT = "implicit"; //$NON-NLS-1$
	static final String SCOPE_READ = "read"; //$NON-NLS-1$
	static final String SCOPE_WRITE = "write"; //$NON-NLS-1$
	static final String TRUST = "trust"; //$NON-NLS-1$
	static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
	static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

		configurer
		.inMemory()
		.withClient(CLIEN_ID)
		.secret(CLIENT_SECRET)
		.authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT )
		.scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
		.accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS).
		refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
		.authenticationManager(authenticationManager);
	}
}
