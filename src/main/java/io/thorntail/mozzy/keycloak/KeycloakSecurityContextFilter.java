package io.thorntail.mozzy.keycloak;

import java.io.IOException;
import java.security.Principal;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.wildfly.swarm.keycloak.deployment.KeycloakSecurityContextAssociation;

@Provider
public class KeycloakSecurityContextFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		final SecurityContext securityContext = requestContext.getSecurityContext();
		final Principal kcPrincipal = () -> {
			return KeycloakSecurityContextAssociation.get().getToken().getPreferredUsername();
		};

		requestContext.setSecurityContext(new SecurityContext() {

			@Override
			public Principal getUserPrincipal() {
				return kcPrincipal;
			}

			@Override
			public boolean isUserInRole(String role) {
				return securityContext.isUserInRole(role);
			}

			@Override
			public boolean isSecure() {
				return securityContext.isSecure();
			}

			@Override
			public String getAuthenticationScheme() {
				return securityContext.getAuthenticationScheme();
			}
		});
	}

}
