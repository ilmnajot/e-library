package uz.ilmnajot.elibrary.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.elibrary.entity.Book;
import uz.ilmnajot.elibrary.enums.Category;

import java.util.List;
import java.util.Optional;

@Repository
@ComponentScan
public interface BookRepository extends JpaRepository<Book, Long> {
//
//    Optional<Book> findBookByBookQuantityLessThan(int bookQuantity, Pageable pageable);
//    Optional<Book> findBookByBookQuantityGreaterThan(int bookQuantity, Pageable pageable);

    Optional<Book> findBookByIsbn(int isbn);
    List<Book> findBooksByIsbn(Pageable pageable, int isbn);
    Optional<Book> findBookByIdAndDeleteFalse(Long id);
//    List<Book> findAllByDeleteFalse(Pageable pageable);
    Optional<Book> findBookByIdAndAvailableTrue(Long pageable);
    Page<Book> findBookByAvailableFalse(Pageable pageable);

//    Page<Book> findBooksByCategory(Category category, Pageable pageable);
    @Query(value="SELECT * FROM book WHERE book.category LIKE %?1%", nativeQuery = true)
    List<Book> findAllBooksByCategory(@Param("category") Category category, Pageable pageable);

    @Query(value = "SELECT  * FROM book WHERE book.book_name LIKE %?1%", nativeQuery = true)
    List<Book> findBookByBookNameIgnoreCase(@Param("book_name") String bookName);

    @Query(value = "select * from book where book.author_name like %?1%", nativeQuery = true)
    List<Book> findBooksByAuthorName(@Param("author_name") String author_name);

//    Optional<Book> findBookByBookCode(int bookCode) ;
}
