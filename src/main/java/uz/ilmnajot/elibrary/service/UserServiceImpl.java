package uz.ilmnajot.elibrary.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.elibrary.entity.User;
import uz.ilmnajot.elibrary.entity.UserBook;
import uz.ilmnajot.elibrary.exception.UserException;
import uz.ilmnajot.elibrary.model.common.ApiResponse;
import uz.ilmnajot.elibrary.model.request.UserRequest;
import uz.ilmnajot.elibrary.model.response.UserResponse;
import uz.ilmnajot.elibrary.repository.BookRepository;
import uz.ilmnajot.elibrary.repository.UserBookRepository;
import uz.ilmnajot.elibrary.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final UserBookRepository userBookRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BookRepository bookRepository, UserBookRepository userBookRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
        this.userBookRepository = userBookRepository;
    }

    @Override
    public ApiResponse addUser(UserRequest request) {
        Optional<User> optionalUser = userRepository.findUserByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserException("User with email " + optionalUser.get());
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGrade(request.getGrade());
        user.setEmail(request.getEmail());
        user.setSchool(request.getSchool());
        user.setAvailable(request.isAvailable());
        return null;
    }

    @Override
    public ApiResponse getUser(Long id) {
        User user = getUserById(id);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return new ApiResponse("success", true, userResponse);
    }

    @Override
    public ApiResponse getAllUser(int page, int size) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, size));
        if (users.isEmpty()) {
            throw new UserException("No users found", HttpStatus.NOT_FOUND);
        }
        List<UserResponse> userResponseList = users
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
        return new ApiResponse("success", true, userResponseList);
    }

    @Override
    public ApiResponse deleteUser(Long id) {
        UserBook user = getUserByIdInUserBook(id);
        int counted = userBookRepository.countByUserIdAndBookStatusTrue(id);
        if (counted == 0) {
            user.setBookStatus(false);
            userBookRepository.save(user);
            return new ApiResponse("success", true, "user deleted successfully");
        }
        throw new UserException("the user has not been deleted because there some books has not been returned yet. books are: " + List.of(user.getBook()), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ApiResponse updateUser(Long userId, UserRequest request) {
        Optional<User> optionalUser = userRepository.findUserByEmailOrId(request.getEmail(), userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setId(userId);
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setGrade(request.getGrade());
            user.setAvailable(request.isAvailable());

            User savedUser = userRepository.save(user);
            UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
            return new ApiResponse("success", true, userResponse);
        }
        throw new UserException("Could not find user with id " + userId, HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse getAllGraduatedStudent(int page, int size) {
        List<UserBook> allActiveUsers = userBookRepository.findAllActiveUsers(PageRequest.of(page, size));
        if (allActiveUsers.isEmpty()) {
            throw new UserException("the there are no active users", HttpStatus.NOT_FOUND);
        }
        List<UserResponse> userResponseStream = allActiveUsers
                .stream()
                .map(userBook -> modelMapper.map(userBook, UserResponse.class))
                .toList();
        return new ApiResponse("success", true, userResponseStream);
    }

    @Override
    public ApiResponse getAllUnGraduatedStudents(int page, int size) {
        Page<User> all = userRepository.findAll(PageRequest.of(page, size));
        if (all.isEmpty()) {
            throw new UserException("there are no graduated students", HttpStatus.NOT_FOUND);
        }
        List<UserResponse> userResponseStream = all
                .stream()
                .map(userBook -> modelMapper.map(userBook, UserResponse.class))
                .toList();
        return new ApiResponse("success", true, userResponseStream);
    }


    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserException("there is no user with the id " + userId, HttpStatus.NOT_FOUND);
    }

    public UserBook getUserByIdInUserBook(Long userId) {
        Optional<UserBook> userBook = userBookRepository.findById(userId);
        if (userBook.isPresent()) {
            return userBook.get();
        }
        throw new UserException("there is no user with the id " + userId, HttpStatus.NOT_FOUND);
    }


}
