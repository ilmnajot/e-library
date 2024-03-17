package uz.ilmnajot.elibrary.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ilmnajot.elibrary.entity.Book;
import uz.ilmnajot.elibrary.entity.User;
import uz.ilmnajot.elibrary.entity.UserBook;
import uz.ilmnajot.elibrary.enums.Category;
import uz.ilmnajot.elibrary.exception.BookException;
import uz.ilmnajot.elibrary.exception.UserException;
import uz.ilmnajot.elibrary.model.common.ApiResponse;
import uz.ilmnajot.elibrary.model.request.BookRequest;
import uz.ilmnajot.elibrary.model.response.BookResponse;
import uz.ilmnajot.elibrary.repository.BookRepository;
import uz.ilmnajot.elibrary.repository.UserBookRepository;
import uz.ilmnajot.elibrary.repository.UserRepository;

import java.util.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;

    @Override
    public ApiResponse addBook(BookRequest request) {
        Optional<Book> bookByIsbn = bookRepository.findBookByIsbn(request.getIsbn());
        if (bookByIsbn.isPresent()) {
            throw new BookException("Book already exists with isbn number: " + request.getIsbn(), HttpStatus.BAD_REQUEST);
        }
        Book book = new Book();
        book.setBookName(request.getBookName());
        book.setAuthorName(request.getAuthorName());
        book.setIsbn(request.getIsbn());
        book.setBookQuantity(request.getBookQuantity());
        book.setAvailable(request.isAvailable());
//        book.setBookCode(request.getBookCode());
        book.setCategory(request.getCategory());
        Book savedBook = bookRepository.save(book);
        BookResponse response = modelMapper.map(savedBook, BookResponse.class);
        return new ApiResponse("success", true, response);
    }


    @Override
    public ApiResponse getBook(Long bookId) {
        Book book = getBookById(bookId);
        BookResponse response = modelMapper.map(book, BookResponse.class);
        return new ApiResponse("the book has been found!", true, response);
    }

    @Override
    public ApiResponse deleteBook(Long id) {
        Book book = getBookByDeletedFalse(id);
        if (book != null) {
            book.setDelete(true);
            book.setAvailable(false);
            bookRepository.save(book);
            return new ApiResponse("success", true, "the book has been deleted");
        }
        throw new BookException("The book is not found", NOT_FOUND);
    }

    @Override
    public ApiResponse updateBook(BookRequest request, Long id) {
        Book book = getBookById(id);
        if (book != null) {
            book.setId(id);
            book.setBookName(request.getBookName());
            book.setAuthorName(request.getAuthorName());
            book.setIsbn(request.getIsbn());
            book.setBookQuantity(request.getBookQuantity());
            book.setAvailable(existsBook(id));
//            book.setBookCode(request.getBookCode());
            book.setCategory(request.getCategory());
            Book savedBook = bookRepository.save(book);
            BookResponse response = modelMapper.map(savedBook, BookResponse.class);
            return new ApiResponse("success", true, response);
        }
        throw new BookException("Book not found", HttpStatus.NOT_FOUND);

    }

    @Override
    public ApiResponse getAllBook(int page, int size) {
        Page<Book> books = bookRepository.findAll(PageRequest.of(page, size));
        if (books.isEmpty()) {
            throw new BookException("there is no any book", NOT_FOUND);
        }
        List<BookResponse> list =
                books
                        .stream()
                        .map(book -> modelMapper.map(book, BookResponse.class))
                        .toList();
        return new ApiResponse("the books have been found", true, list);
    }

    @Override
    public ApiResponse getAllAvailableBook(int page, int size) {
        Page<Book> books = bookRepository.findBookByAvailableFalse(PageRequest.of(page, size));
        List<BookResponse> bookResponseStream = books
                .stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
        return new ApiResponse("success", true, bookResponseStream);
    }

    @Override
    public ApiResponse getAllNotAvailableBook(int page, int size) {
        Page<Book> books = bookRepository.findBookByAvailableFalse(PageRequest.of(page, size));
        if (books.isEmpty()) {
            throw new BookException("there is no book", NOT_FOUND);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("suggestion_list", toDtoList(books.toList()));
        response.put("currentPage", books.getNumber());
        response.put("totalPages", books.getTotalPages());
        response.put("totalItems", books.getTotalElements());
            return new ApiResponse("the books have been found", true, response);
    }

    @Override
    public ApiResponse getBookByIsbn(int page, int size, int isbn) {
        Optional<Book> book = bookRepository.findBookByIsbn(isbn);
        if (book.isPresent()) {
            BookResponse mappedBook = modelMapper.map(book, BookResponse.class);
            return new ApiResponse("success", true, mappedBook);
        }
        throw new BookException("there is no book with code " + isbn, HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse getBooksByAuthor(int page, int size, String authorName) {
        List<Book> books = bookRepository.findBooksByAuthorName(authorName);
        if (books.isEmpty()) {
            throw new BookException("there is no any book with authorName: " + authorName, NOT_FOUND);
        }
        List<BookResponse> list = books
                .stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();

        return new ApiResponse("the books have been found", true, list);
    }

    @Override
    public ApiResponse getBookByBookName(String bookName) {
        List<Book> books = bookRepository.findBookByBookNameIgnoreCase(bookName);
        if (books.isEmpty()) {
            throw new BookException("there is no any book with the name " + bookName, NOT_FOUND);
        }
        List<BookResponse> list = books
                .stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
        return new ApiResponse("the books have been found", true, list);
    }

    @Override
    public ApiResponse getBooksByCategory(int page, int size, Category category) {
        List<Book> books = bookRepository.findAllBooksByCategory(category, PageRequest.of(page, size));
        if (!books.isEmpty()) {
            List<BookResponse> list = books
                    .stream()
                    .map(book -> modelMapper.map(book, BookResponse.class))
                    .toList();
            return new ApiResponse("success", true, list);
        }
        throw new BookException("books has not been found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse incrementBook(Long bookId, int incrementAmount) {
        Optional<Book> optionalBook = bookRepository.findBookByIdAndDeleteFalse(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setBookQuantity(book.getBookQuantity() + incrementAmount);
            Book savedBook = bookRepository.save(book);
            BookResponse response = modelMapper.map(savedBook, BookResponse.class);
            return new ApiResponse("success", true, response);
        }
        throw new BookException("Book not found", NOT_FOUND);
    }

    @Override
    public ApiResponse decrementBook(Long bookId, int decrementAmount) {
        Optional<Book> optionalBook = bookRepository.findBookByIdAndDeleteFalse(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            int max = book.getBookQuantity() - decrementAmount;
            if (max >= 0) {
                book.setBookQuantity(book.getBookQuantity() - decrementAmount);
                Book savedBook = bookRepository.save(book);
                BookResponse response = modelMapper.map(savedBook, BookResponse.class);
                return new ApiResponse("success", true, response);

            } else {
                throw new BookException("you reached the limit", HttpStatus.BAD_REQUEST);
            }
        }
        throw new BookException("there is no book with id " + bookId, NOT_FOUND);
    }

    @Transactional
    @Override
    public ApiResponse registerBookToUser(Long bookId, Long userId) {

        User user = getUserById(userId);
        Book book = getBookById(bookId);

        UserBook userBook = new UserBook();

        boolean isBookAlreadyRegistered = userBookRepository.existsByUserAndBook(user, book);

        if (isBookAlreadyRegistered) {
            throw new BookException("The book with ID " + bookId + " is already registered to the user with ID " + userId, HttpStatus.BAD_REQUEST);
        }
        if (book.getBookQuantity() <= 0) {
            throw new BookException("No available copies of the book with ID " + bookId + " to register to the user with ID " + userId, HttpStatus.BAD_REQUEST);
        }
        userBook.setUser(user);
        userBook.setBook(book);

        book.setBookQuantity(book.getBookQuantity() - 1);

        userBookRepository.save(userBook);
        bookRepository.save(book);
        return new ApiResponse("success", true, "the book has been registered to the user");

    }


    public ApiResponse returnABook(Long bookId, Long userId) {
        User user = getUserById(userId);
        Book book = getBookById(bookId);

        // Check if the book is registered to the user
        UserBook userBook = userBookRepository.findByUserAndBook(user, book)
                .orElseThrow(() -> new BookException("The book with ID " + bookId + " is not registered to the user with ID " + userId, HttpStatus.BAD_REQUEST));

        userBookRepository.save(userBook);
        book.setBookQuantity(book.getBookQuantity() + 1);
        bookRepository.save(book);

        return new ApiResponse("Success", true, "The book has been returned by the user");
    }

    @Override
    public ApiResponse returnBook(Long bookId, Long userId) {

        UserBook userBook = new UserBook();

        User user = getUserById(userId);
        Book book = getBookById(bookId);

        UserBook userAndBook = userBookRepository.findByUserAndBook(user, book)
                .orElseThrow(() -> new BookException("The book with ID " + bookId + " is not registered to the user with ID " + userId, HttpStatus.BAD_REQUEST));

        UserBook userBookById = getUserBookById(userId);
        UserBook bookByBookId = getUserBookByBookId(bookId);
        return null;
//        if (userBookById.getBookId().equals(bookByBookId.getId())) {
//            user.setLoanedBook(user.getLoanedBook() - 1);
//            book.setBookQuantity(book.getBookQuantity() + 1);
//            book.setBookStatus(BookStatus.RETURNED);
//            userRepository.save(user);
//            bookRepository.save(book);
//            return new ApiResponse("success", true, "the book with id " + bookId + " is returned successfully");
//        }
//        throw new BookException("the book with id " + bookId + " is not found", HttpStatus.NOT_FOUND);
    }
//    @Override
//    public ApiResponse getBookById(Long bookId) {
//        Book book = getBookById(bookId);

//        BookResponse mappedBook = modelMapper.map(book, BookResponse.class);

//        return new ApiResponse("success", true, mappedBook);

//    }


//    @Override
//    public ApiResponse getAllNotAvailableBook(int page, int size) {
//        List<BookResponse> bookResponses = userService.getBookResponses(page, size);
//        return new ApiResponse("success", true, bookResponses);
//    }

//    @Override
//    public ApiResponse getAllMyBook(Long userId) {
//        return null;
//    }


//    @Override
//    public ApiResponse getBooksByAuthor(int page, int size, String author_name) {
//        ApiResponse booksByAuthor = userService.getBooksByAuthor(page, size, author_name);
//        return new ApiResponse("success", true, booksByAuthor);
//    }

    //    @Override
//    public ApiResponse getBookToUser(Long bookId, Long userId, int bookCode) {
//        Book book = getBookByBookCode(bookCode);
//        User user = getUserById(userId);
//        if (bookCode==user.getBook().getBookCode()){
//            throw new BookException("the book is already registered with the code: " + bookCode, HttpStatus.BAD_REQUEST);
//        }
//        if (book.getBookQuantity() > 0) {
//            user.setBookId(bookId);
//            book.setUserId(userId);
//            user.setLoanedBook(user.getLoanedBook() + 1);
//            book.setBookQuantity(book.getBookQuantity() - 1);
//            userRepository.save(user);
//            bookRepository.save(book);
//            return new ApiResponse("success", true, "the book has been registered to the user");
//        }
//        throw new BookException("there is no enough book to register", HttpStatus.BAD_REQUEST);
//    }


    public UserBook getUserBookById(Long userId) {
        Optional<UserBook> user = userBookRepository.findUserBookByUserId(userId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("the user is not found with id " + userId, HttpStatus.NOT_FOUND);
    }

    public UserBook getUserBookByBookId(Long bookId) {
        Optional<UserBook> book = userBookRepository.findUserBookByBookId(bookId);
        if (book.isPresent()) {
            return book.get();
        }
        throw new BookException("Could not find book with id " + bookId, HttpStatus.NOT_FOUND);
    }


    public Book getBookByDeletedFalse(Long bookId) {
        Optional<Book> book = bookRepository.findBookByIdAndDeleteFalse(bookId);
        if (book.isPresent()) {
            return book.get();
        }
        throw new BookException("there is no book with id " + bookId, HttpStatus.NOT_FOUND);
    }

    public Book getBookById(Long bookId) {
        Optional<Book> book = bookRepository.findBookByIdAndAvailableTrue(bookId);
        if (book.isPresent()) {
            return book.get();
        }
        throw new BookException("there is no book with id " + bookId, HttpStatus.NOT_FOUND);
    }


    public boolean existsBook(Long bookId) {
        Book book = getBookById(bookId);
        if (book.getBookQuantity() == 0) {
            book.setAvailable(false);
            return false;
        }
        book.setAvailable(true);
        return true;
    }


    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findUserByIdAndAvailableTrue(userId);
        if (optionalUser.isEmpty()) {
            throw new UserException("there is no user with the given id: " + userId, HttpStatus.NOT_FOUND);
        }
        return optionalUser.get();
    }

//    public List<BookResponse> getBookResponses(int page, int size) {
//        Page<Book> books = bookRepository.findBookByAvailableFalse(PageRequest.of(page, size));
//        if (books.isEmpty()) {
//            throw new BookException("there is no book", NOT_FOUND);
//        }
//        Map<String, Object> response = new HashMap<>();
//        response.put("suggestion_list", toDtoList(books.toList()));
//        response.put("currentPage", books.getNumber());
//        response.put("totalPages", books.getTotalPages());
//        response.put("totalItems", books.getTotalElements());
//        return (List<BookResponse>) new ApiResponse("success", true, response);
//    }

    public static BookResponse toDto(Book book) {
        BookResponse response = new BookResponse();
        response.setBookName(book.getBookName());
        response.setAuthorName(book.getAuthorName());
        response.setIsbn(book.getIsbn());
        response.setBookQuantity(book.getBookQuantity());
        response.setAvailable(book.isAvailable());
        response.setCategory(book.getCategory());
        return response;
    }

    public static List<BookResponse> toDtoList(List<Book> books) {
        List<BookResponse> bookResponses = new ArrayList<>();
        for (Book book : books) {
            bookResponses.add(toDto(book));
        }
        return bookResponses;
    }
}
