package uz.ilmnajot.elibrary.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Library",
                        url = "https://sampm.uz",
                        email = "ilmnajot2021@gmail.com"
                ),
                title = "Library_system in SamPS - Elbek_Umar",
                description = "This website is for using in library in only SamPS",
                version = "1.0",
                license = @License(
                        name = "MIT License",
                        url = "http://www.apache.org/mit/mitLicense"
                ),
                termsOfService = "Terms of Service"
        ),
        servers = {
                @Server(
                        description = "Server description here",
                        url = "http://localhost:1111"
                ),
                @Server(
                        description = "Server description here",
                        url = "http://localhost:1111"

                )
        }
)
/*@SecurityScheme(
        name = "Bearer",
        description = "Bearer token description here",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)*/
@Configuration
//@OpenAPIDefinition
public class OpenApiConfig {
//
//    @Value("${openapi.samps-url}")
//    private String url;
//
//    @Bean
//    public OpenAPI openAPI() {
//        Server server = new Server();
//        server.setUrl(url);
//        server.setDescription("Open API documentation - SAMPS");
//
//        Contact contact = new Contact()
//
//                .email("samps@gmail.com")
//                .name("SamPS library")
//                .url("https://samps.uz");
//
//
//        License license = new License()
//                .name("MIT License")
//                .url("https://opensource.org/licenses/MIT");
//
//        Info info = new Info()
//                .title("Open API documentation - SAMPS library")
//                .version("1.1")
//                .contact(contact)
//                .description("Open API documentation - SAMPS library")
//                .termsOfService("https://wwww.brainone.com/terms")
//                .license(license);
//        return new OpenAPI()
//                .info(info)
//                .servers(List.of(server));
//    }
//}
}
