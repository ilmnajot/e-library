package uz.ilmnajot.elibrary.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import uz.ilmnajot.elibrary.entity.User;
import uz.ilmnajot.elibrary.exception.UserException;
import uz.ilmnajot.elibrary.model.common.ApiResponse;
import uz.ilmnajot.elibrary.model.request.BookRequest;
import uz.ilmnajot.elibrary.model.request.LoginRequest;
import uz.ilmnajot.elibrary.model.request.UserRequest;
import uz.ilmnajot.elibrary.model.response.LoginResponse;
import uz.ilmnajot.elibrary.model.response.UserResponse;
import uz.ilmnajot.elibrary.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
/*    private final JwtProvider jwtProvider;*/

    public AuthServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public ApiResponse login(LoginRequest request) {
        /*authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        String token = jwtProvider.generateToken(request.getEmail());*/
       /* LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return new ApiResponse("success", true, loginResponse);*/
        return null;
    }

}
