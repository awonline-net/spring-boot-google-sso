package net.awonline.microservices.auth.oauth2.config;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.oauth2.client.OAuth2ClientContext;

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().and().authorizeRequests().antMatchers("/public").permitAll().antMatchers("/user")
				.authenticated().antMatchers("/secured").hasRole("ADMIN").anyRequest().authenticated();
		// .and().addFilterBefore(ssoFilter(), FilterSecurityInterceptor.class);
	}

	// private Filter ssoFilter() {
	// OAuth2ClientAuthenticationProcessingFilter facebookFilter = new
	// OAuth2ClientAuthenticationProcessingFilter(
	// "/login");
	// OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook,
	// oauth2ClientContext);
	// facebookFilter.setRestTemplate(facebookTemplate);
	// UserInfoTokenServices tokenServices = new
	// UserInfoTokenServices(resourceServerProperties.getUserInfoUri(),
	// facebook.getClientId());
	// tokenServices.setRestTemplate(facebookTemplate);
	// tokenServices.setAuthoritiesExtractor(new
	// CustomSocialAuthoritiesExtractor());
	// facebookFilter.setTokenServices(tokenServices);
	// return facebookFilter;
	// }
	//
	// @Autowired
	// AuthorizationCodeResourceDetails facebook;
	//
	// @Autowired
	// ResourceServerProperties resourceServerProperties;

	@Bean
	public AuthoritiesExtractor customSocialAuthoritiesExtractor() {
		return new CustomSocialAuthoritiesExtractor();
	}

	public class CustomSocialAuthoritiesExtractor implements AuthoritiesExtractor {

		@Override
		public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
			// TODO

			boolean userExist = true;
			if (!userExist) {
				// throw new IllegalStateException("User does not exists");
				// return new ArrayList<>();
				throw new BadCredentialsException("User does not exists");
			}

			String authorities = "ROLE_ADMIN";
			return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
		}
	}

	///
}
