package com.smarthomestay;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Bedy Briliant Wijaya",
                        email = "wijaya.bedybriliant@gmail.com"
                )
        )
)
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
