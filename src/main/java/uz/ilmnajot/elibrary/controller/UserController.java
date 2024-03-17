package uz.ilmnajot.elibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.elibrary.model.common.ApiResponse;
import uz.ilmnajot.elibrary.model.request.UserRequest;
import uz.ilmnajot.elibrary.service.UserService;

import static uz.ilmnajot.elibrary.utils.Constants.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    //*******************************USERS****************************//

    @PostMapping(ADD_USER)
    public HttpEntity<ApiResponse> addUser(@RequestBody UserRequest request) {
        ApiResponse teacher = userService.addUser(request);
        return teacher != null
                ? ResponseEntity.ok(teacher)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_USER)
    public HttpEntity<ApiResponse> getUser(@PathVariable Long id) {
        ApiResponse teacher = userService.getUser(id);
        return teacher != null
                ? ResponseEntity.ok(teacher)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_ALL_USER)
    public HttpEntity<ApiResponse> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse allTeachers = userService.getAllUser(page, size);
        return allTeachers != null
                ? ResponseEntity.status(HttpStatus.OK).body(allTeachers)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(DELETE_USER)
    public HttpEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        ApiResponse teacher = userService.deleteUser(id);
        return teacher != null
                ? ResponseEntity.status(HttpStatus.OK).body(teacher)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping(UPDATE_USER)
    public HttpEntity<ApiResponse> updateUser(
            @PathVariable(name = "userId") Long userId,
            @RequestBody UserRequest request) {
        ApiResponse user = userService.updateUser(userId, request);
        return user != null
                ? ResponseEntity.status(HttpStatus.OK).body(user)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_ALL_GRADUATED_STUDENT)
    public HttpEntity<ApiResponse> getAllGraduatedStudents(@RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse allGraduatedStudent = userService.getAllGraduatedStudent(page, size);
        return allGraduatedStudent != null
                ? ResponseEntity.status(HttpStatus.OK).body(allGraduatedStudent)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_ALL_UNGRADUATED_STUDENT)
    public HttpEntity<ApiResponse> getAllUnGraduatedStudents(@RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse allGraduatedStudent = userService.getAllUnGraduatedStudents(page, size);
        return allGraduatedStudent != null
                ? ResponseEntity.status(HttpStatus.OK).body(allGraduatedStudent)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}


