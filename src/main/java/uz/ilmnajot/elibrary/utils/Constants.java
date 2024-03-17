package uz.ilmnajot.elibrary.utils;

public class Constants {

    public final static String AUTH = "/api/auth";
    public static  final String REGISTER_USER = "/register_user";
    public static  final String REGISTER_STUDENT = "/register_student";
    public static  final String VERIFY_STUDENT = "/verify_student";
    public static  final String LOGIN = "/login";
    public static final String ADD_STUDENT = "/add_student";
    public static final String GET_STUDENT = "/get_student/{id}";
    public static final String GET_ALL_STUDENT = "/get_all_student";
    public static final String GET_ALL_UNGRADUATED_STUDENT = "/get_all_ungraduated_student";
    public static final String GET_ALL_GRADUATED_STUDENT = "/get_all_graduated_student";
    public static final String UPDATE_STUDENT = "/update_student/{userId}";
    public static final String GRADUATE_STUDENT = "/graduate_student/{userId}";
    public static final String DELETE_STUDENT = "/delete_student/{id}";
    public static final String BOOK_TO_USER = "/book_to_user/{bookId}/{userId}";





    public static final String GET_BOOK = "/get_book/{bookId}";
    public static final String SEARCH = "/search";
    public static final String GET_ALL_BOOK = "/get_all_book";
    public static final String UPDATE_BOOK = "/update_book/{id}";
    public static final String DELETE_BOOK = "/delete_book/{id}";
    public static final String ADD_BOOK = "/add_book";
    public static final String GET_SOME_BOOKS = "/getSomeBooks/{bookId}/{userId}";
    public static final String INCREASE_BOOK = "/increase_book/{bookId}";
    public static final String DECREASE_BOOK = "/decrease_book/{bookId}";
    public static final String GET_ALL_DELETED_BOOK= "/get_all_deleted_book";
    public static final String GET_ALL_AVAILABLE_BOOK= "/get_all_available_book";
    public static final String GET_ALL_NOT_AVAILABLE_BOOK= "/get_all_not_available_book";
    public static final String GET_MY_BOOK= "/get_my_book/{userId}";
    public static final String GET_BOOKS_BY_CATEGORY= "/get_books_by_category";
    public static final String GET_BOOKS_BY_ISBN= "/get_books_by_isbn";
    public static final String GET_BOOKS_BY_AUTHOR= "/get_books_by_author";
    public static final String RETURN_BOOK = "/return_book/{bookId}/{userId}";


    //********************TEACHERS****************

    public static final String ADD_USER = "/add_user";
    public static final String GET_USER = "/get_user/{id}";
    public static final String GET_ALL_USER = "/get_all_user";
    public static final String GET_ALL_NON_EXIST_TEACHER = "/get_all_non_exist_user";
    public static final String UPDATE_USER = "/update_user/{userId}";
    //    public static final String GRADUATE_STUDENT = "/graduate_student/{studentId}";
    public static final String DELETE_USER = "/delete_user/{id}";
//    public static final String BOOK_TO_USER = "/book_to_teacher/{bookId}/{teacherId}";
//    public static final String RETURN_BOOK = "/return_book/{userId}/{bookId}";
//    public static final String BOOK_TO_USER = "/book_to_user/{userId}/{bookId}";

}
