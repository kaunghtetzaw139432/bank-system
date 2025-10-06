package com.musdon.the_java_academy_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
			title = "The Java Academy Bank App",
			description = "Backend Rest API for KBZ Bank",
			version = "v1.0",
			contact = @Contact(
				name="Kaung Htet Zaw",
				email = "zawk29006@gmail.com",
				url = "http://github.com/Monywa/KBZ_bank_app"
			),
			license = @License(
				name = "The Java Academy",
				url = "https://github.com/Monywa/KBZ_bank_app"
			)

		),
		externalDocs = @ExternalDocumentation(
			description = "The Java Academy Bank App Documentatiion",
			url = "https://github.com/Monywa/KBZ_bank_app"
		)
)
public class TheJavaAcademyBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheJavaAcademyBankApplication.class, args);
	}

}
