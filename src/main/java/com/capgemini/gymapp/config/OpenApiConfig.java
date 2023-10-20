package com.capgemini.gymapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Naoufel Ferjani",
                        email = "ferjani.naoufel22@gmail.com",
                        url="http://localhost:3031"
                ),
                description = "OpenApi documaentaion for GymApp",
                title = "GymApp Spec",
                version = "1.0",
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Backend Server",
                        url = "http://localhost:8089"
                ),
                @Server(
                        description = "Fronend Server",
                        url = "http://localhost:3031"
                )
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description ="jwt Auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
