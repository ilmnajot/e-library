package uz.ilmnajot.elibrary.service;


import uz.ilmnajot.elibrary.enums.Category;
import uz.ilmnajot.elibrary.model.common.ApiResponse;
import uz.ilmnajot.elibrary.model.request.UserRequest;
import uz.ilmnajot.elibrary.model.response.BookResponse;

import java.util.List;

public interface UserService {


    ApiResponse addUser(UserRequest request);

    ApiResponse getUser(Long id);

    ApiResponse getAllUser(int page, int size);

    ApiResponse deleteUser(Long id);

    ApiResponse updateUser(Long userId, UserRequest request);

    ApiResponse getAllGraduatedStudent(int page, int size);

    ApiResponse getAllUnGraduatedStudents(int page, int size);
}
