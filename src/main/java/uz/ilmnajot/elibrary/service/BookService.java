package uz.ilmnajot.elibrary.service;


import uz.ilmnajot.elibrary.enums.Category;
import uz.ilmnajot.elibrary.model.common.ApiResponse;
import uz.ilmnajot.elibrary.model.request.BookRequest;
import uz.ilmnajot.elibrary.model.response.BookResponse;

import java.util.List;

public interface BookService {
//    ApiResponse returnBook(Long bookId, Long userId);

    ApiResponse addBook(BookRequest request);

    ApiResponse registerBookToUser(Long bookId, Long userId);

    ApiResponse incrementBook(Long bookId, int incrementAmount);

    ApiResponse decrementBook(Long bookId, int decrementAmount);

    ApiResponse returnBook(Long bookId, Long userId);

    ApiResponse updateBook(BookRequest request, Long id);

    ApiResponse deleteBook(Long id);

    ApiResponse getBook(Long bookId);

    ApiResponse getAllBook(int page, int size);

    ApiResponse getAllAvailableBook(int page, int size);

    ApiResponse getAllNotAvailableBook(int page, int size);

    ApiResponse getBooksByCategory(int page, int size, Category category);

    ApiResponse getBooksByAuthor(int page, int size, String author_name);

    ApiResponse getBookByBookName(String bookName);
//    List<BookResponse> getBookResponses(int page, int size);

    ApiResponse getBookByIsbn(int page, int size, int isbn);
}
