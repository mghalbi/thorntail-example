package io.thorntail.mozzy.api;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;


/**
 * @author Moshen Ghalbi
 */
@ApplicationPath("/api")
@SwaggerDefinition (
info = @Info (
        title = "Example Service with thornatil",
        description = "A simple example here",
        version = "1.0.0",
        contact = @Contact (
            name = "Mohsen Ghali",
            email = "mohsenghalbi@gmail.com"
        )
    ),
    host = "localhost",
    basePath = "/api",
    schemes = {SwaggerDefinition.Scheme.HTTP}
)
@ApplicationScoped
@DeclareRoles({ "Manager", "USER" })
public class JaxRsActivator extends Application {
	

    @PersistenceContext
    @Produces
    private EntityManager entityManager;

}
