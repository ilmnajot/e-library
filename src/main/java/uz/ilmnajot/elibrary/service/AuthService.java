package uz.ilmnajot.elibrary.service;


import uz.ilmnajot.elibrary.model.common.ApiResponse;
import uz.ilmnajot.elibrary.model.request.BookRequest;
import uz.ilmnajot.elibrary.model.request.LoginRequest;
import uz.ilmnajot.elibrary.model.request.UserRequest;

public interface AuthService {


    ApiResponse login(LoginRequest request);


}
