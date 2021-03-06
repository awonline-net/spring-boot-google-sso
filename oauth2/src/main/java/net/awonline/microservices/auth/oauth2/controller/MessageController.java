package net.awonline.microservices.auth.oauth2.controller;

import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

	@RequestMapping(value = "/public", method = RequestMethod.GET)
	public String publicMethod() {
		return "Public";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/secured", method = RequestMethod.GET)
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public String securedMethod(OAuth2Authentication authentication) {

		if (!(authentication.getUserAuthentication().getDetails() instanceof Map)) {
			throw new IllegalArgumentException("Authentication details are not a map");
		}

		Map<String, String> authenticationDetails = (Map<String, String>) authentication.getUserAuthentication()
				.getDetails();
		String email = authenticationDetails.get("email");
		if (email == null || email.trim().length() == 0) {
			throw new IllegalArgumentException("Authenticated user's email is empty");
		}

		return "Secured (" + email + ")";
	}
}
