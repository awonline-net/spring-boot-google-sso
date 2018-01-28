package net.awonline.microservices.auth.oauth2.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:on
		http.formLogin().and()
				.authorizeRequests()
				.antMatchers("/public").permitAll()
				.antMatchers("/user")
				.authenticated()
				.antMatchers("/secured").hasRole("ADMIN")
			.anyRequest().authenticated();
		// @formatter:off
	}

	@Bean
	public AuthoritiesExtractor customSocialAuthoritiesExtractor() {
		return new CustomSocialAuthoritiesExtractor();
	}

	private class CustomSocialAuthoritiesExtractor implements AuthoritiesExtractor {

		@Override
		public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {

			boolean userExist = true; // TODO check if the user is permitted to log in
			if (!userExist) {
				throw new BadCredentialsException("User does not exists");
			}

			String authorities = "ROLE_ADMIN"; // TODO load authorities
			return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
		}
	}
}
