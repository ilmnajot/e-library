package uz.ilmnajot.elibrary.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.elibrary.enums.Category;
import uz.ilmnajot.elibrary.model.common.ApiResponse;
import uz.ilmnajot.elibrary.model.request.BookRequest;
import uz.ilmnajot.elibrary.service.BookService;

import static uz.ilmnajot.elibrary.utils.Constants.*;
import static uz.ilmnajot.elibrary.utils.Constants.DECREASE_BOOK;
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;


    @PostMapping(ADD_BOOK) //working well
    public HttpEntity<ApiResponse> addBook(@RequestBody BookRequest request) {
        ApiResponse book = bookService.addBook(request);
        return book != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(book)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_BOOK) //working well
    public HttpEntity<ApiResponse> getBook(@PathVariable(name = "bookId") Long bookId) {
        ApiResponse book = bookService.getBook(bookId);
        return ResponseEntity.status(book.isSuccess() ? 200 : 409).body(book);
    }

    @GetMapping(GET_ALL_BOOK) //working well
    public HttpEntity<ApiResponse> getAllBook(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse allBook = bookService.getAllBook(page, size);
        return allBook != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(allBook)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping(UPDATE_BOOK) //working well
    public HttpEntity<ApiResponse> updateBook(@RequestBody BookRequest request, @PathVariable Long id) {
        ApiResponse book = bookService.updateBook(request, id);
        return book != null
                ? ResponseEntity.ok(book)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping(DELETE_BOOK) // working well
    public HttpEntity<ApiResponse> deleteBook(@PathVariable Long id) {
        ApiResponse book = bookService.deleteBook(id);
        return book != null
                ? ResponseEntity.ok(book)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_ALL_AVAILABLE_BOOK) //working well
    public HttpEntity<ApiResponse> getAllAvailableBook(@RequestParam(name = "page", defaultValue = "0") int page,
                                                       @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse allDeletedBook = bookService.getAllAvailableBook(page, size);
        return allDeletedBook != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(allDeletedBook)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_ALL_NOT_AVAILABLE_BOOK) //not clear yet, seems working well
    public HttpEntity<ApiResponse> getAllNotAvailableBook(@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "9") int size) {
        ApiResponse allDeletedBook = bookService.getAllNotAvailableBook(page, size);
        return allDeletedBook != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(allDeletedBook)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(GET_BOOKS_BY_ISBN) //working well
    public HttpEntity<ApiResponse> getBookByIsbn(@RequestParam(name = "page", defaultValue = "0") int page,
                                                     @RequestParam(name = "size", defaultValue = "9") int size,
                                                     @RequestParam(name = "isbn") int isbn) {
        ApiResponse booksByCategory = bookService.getBookByIsbn(page, size, isbn);
        return booksByCategory != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(booksByCategory)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(GET_BOOKS_BY_CATEGORY) //working well
    public HttpEntity<ApiResponse> getAllBooksByCategory(@RequestParam(name = "page", defaultValue = "0") int page,
                                                         @RequestParam(name = "size", defaultValue = "9") int size,
                                                         @RequestParam(name = "category") Category category) {
        ApiResponse booksByCategory = bookService.getBooksByCategory(page, size, category);
        return booksByCategory != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(booksByCategory)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(GET_BOOKS_BY_AUTHOR) //working well
    public HttpEntity<ApiResponse> getAllBooksByAuthor(@RequestParam(name = "page", defaultValue = "0") int page,
                                                       @RequestParam(name = "size", defaultValue = "9") int size,
                                                       @RequestParam(name = "author_name") String author_name) {
        ApiResponse booksByAuthor = bookService.getBooksByAuthor(page, size, author_name);
        return booksByAuthor != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(booksByAuthor)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(BOOK_TO_USER)
    public HttpEntity<ApiResponse> bookToUser(
            @PathVariable(name = "bookId") Long bookId,
            @PathVariable(name = "userId") Long userId) {
        ApiResponse getBookToTeacher = bookService.registerBookToUser(bookId, userId);
        return getBookToTeacher != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(getBookToTeacher)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(RETURN_BOOK)
    public HttpEntity<ApiResponse> returnBook(
            @PathVariable(name = "bookId") Long bookId,
            @PathVariable(name = "userId") Long userId) {
        ApiResponse apiResponse = bookService.returnBook(bookId, userId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(SEARCH) //working but with upper case si not working
    public HttpEntity<ApiResponse> getBookByName(@RequestParam(name = "book_name") String bookName) {
        ApiResponse book = bookService.getBookByBookName(bookName);
        return ResponseEntity.status(book.isSuccess() ? 200 : 409).body(book);
    }


    @PostMapping(INCREASE_BOOK)
    public HttpEntity<ApiResponse> incrementBook(
            @PathVariable(name = "bookId") Long bookId,
            @RequestParam(name = "increment_amount") int increment_amount) {
        ApiResponse apiResponse = bookService.incrementBook(bookId, increment_amount);
        return apiResponse != null
                ? ResponseEntity.ok(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(DECREASE_BOOK) // working well when amount is more than 0, NO WORKING WHEN IT IS EQUAL TO 0.
    public HttpEntity<ApiResponse> decrementBook(
            @PathVariable(name = "bookId") Long bookId,
            @RequestParam(name = "decrement_amount") int decrement_amount) {
        ApiResponse apiResponse = bookService.decrementBook(bookId, decrement_amount);
        return apiResponse != null
                ? ResponseEntity.ok(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
//    @PostMapping(BOOK_TO_USER)
//    public HttpEntity<ApiResponse> bookToUser(
//            @PathVariable(name = "bookId") Long bookId,
//            @PathVariable(name = "userId") Long userId) {
//        ApiResponse bookToStudent = bookService.getBookToUser(bookId, userId);
//        return bookToStudent != null
//                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(bookToStudent)
//                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }
//    @GetMapping(GET_MY_BOOK) // NO WORKING
//    public HttpEntity<ApiResponse> getMyBook(
//            @PathVariable(name = "userId") Long userId) {
//        ApiResponse allMyBook = bookService.getAllMyBook(userId);
//        return allMyBook != null
//                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(allMyBook)
//                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }

//    @GetMapping(GET_BOOK)
//    public HttpEntity<ApiResponse> getBook(@PathVariable(name = "bookId") Long bookId) {
//        ApiResponse book = userService.getBook(bookId);
//        return ResponseEntity.status(book.isSuccess() ? 200 : 409).body(book);
//    }

//    @GetMapping(GET_ALL_BOOK)
//    public HttpEntity<ApiResponse> getAllBook(@RequestParam(name = "page", defaultValue = "0") int page,
//                                              @RequestParam(name = "size", defaultValue = "9") int size) {
//        ApiResponse allBook = userService.getAllBook(page, size);
//        return allBook != null
//                ? ResponseEntity.status(HttpStatus.FOUND).body(allBook)
//                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }

//    @GetMapping(GET_ALL_AVAILABLE_BOOK)
//    public HttpEntity<ApiResponse> getAllAvailableBook(@RequestParam(name = "page", defaultValue = "0") int page,
//                                                       @RequestParam(name = "size", defaultValue = "9") int size) {
//        ApiResponse allDeletedBook = userService.getAllAvailableBook(page, size);
//        return allDeletedBook != null
//                ? ResponseEntity.status(HttpStatus.FOUND).body(allDeletedBook)
//                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }
//
//    @GetMapping(GET_ALL_NOT_AVAILABLE_BOOK)
//    public HttpEntity<ApiResponse> getAllNotAvailableBook(@RequestParam(name = "page", defaultValue = "0") int page,
//                                                          @RequestParam(name = "size", defaultValue = "9") int size) {
//        ApiResponse allDeletedBook = userService.getAllNotAvailableBook(page, size);
//        return allDeletedBook != null
//                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(allDeletedBook)
//                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }
//
//
//    @GetMapping(GET_BOOKS_BY_CATEGORY)
//    public HttpEntity<ApiResponse> getAllBooksByCategory(@RequestParam(name = "page", defaultValue = "0") int page,
//                                                         @RequestParam(name = "size", defaultValue = "9") int size,
//                                                         @RequestParam(name = "category") Category category) {
//        ApiResponse booksByCategory = userService.getBooksByCategory(page, size, category);
//        return booksByCategory != null
//                ? ResponseEntity.status(HttpStatus.FOUND).body(booksByCategory)
//                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
//
//    @GetMapping(GET_BOOKS_BY_AUTHOR)
//    public HttpEntity<ApiResponse> getAllBooksByAuthor(@RequestParam(name = "page", defaultValue = "0") int page,
//                                                       @RequestParam(name = "size", defaultValue = "9") int size,
//                                                       @RequestParam(name = "authorName") String  authorName) {
//        ApiResponse booksByCategory = userService.getBooksByAuthor(page, size, authorName);
//        return booksByCategory != null
//                ? ResponseEntity.status(HttpStatus.FOUND).body(booksByCategory)
//                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
//
//    @GetMapping(SEARCH)
//    public HttpEntity<ApiResponse> getBookByName(@RequestParam String bookName) {
//        ApiResponse book = userService.getBookByBookName(bookName);
//        return ResponseEntity.status(book.isSuccess() ? 200 : 409).body(book);
//    }

}
