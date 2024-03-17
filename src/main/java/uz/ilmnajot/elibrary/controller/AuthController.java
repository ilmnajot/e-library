package uz.ilmnajot.elibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.elibrary.model.request.UserRequest;
import uz.ilmnajot.elibrary.service.AuthService;
import uz.ilmnajot.elibrary.model.common.ApiResponse;
import uz.ilmnajot.elibrary.model.request.LoginRequest;

import static uz.ilmnajot.elibrary.utils.Constants.*;

@Tag(name = "samps_library", description = "The BookService")
@CrossOrigin(origins = "http://localhost:8888")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

//    @Operation(
//            summary = "login to the system or get token here",
//            tags = {"login", "post"}
//    )
//    @ApiResponses({
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(
//                    responseCode = "201",
//                    content = {
//                            @Content(schema =
//                            @Schema(implementation = AuthController.class), mediaType = "application/json")}),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", content = {@Content(schema = @Schema)})})
//
    @PostMapping(LOGIN)
    public HttpEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        ApiResponse login = authService.login(request);
        System.out.println("token: " + login);
        return login != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(login)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
