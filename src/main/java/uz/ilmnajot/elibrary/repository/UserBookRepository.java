package uz.ilmnajot.elibrary.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.elibrary.entity.Book;
import uz.ilmnajot.elibrary.entity.User;
import uz.ilmnajot.elibrary.entity.UserBook;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    Optional<UserBook> findUserBookByBookId(Long id);

    Optional<UserBook> findUserBookByUserId(Long id);

    boolean existsByUserAndBook(User user, Book book);
    Optional<UserBook> findByUserAndBook(User user, Book book);

    @Query(value = "SELECT * FROM book WHERE id = ?1", nativeQuery = true)
    Optional<Book> findBookById(Long id);

//    Optional<UserBook> findByUserIdAndActiveTrue(Long user_id);

//    List<UserBook> findAllByActiveTrue();

    //    @Query(value = "SELECT * FROM users WHERE user.active=true", nativeQuery = true)
    @Query(value = "SELECT * FROM users WHERE available=true",
            countQuery = "SELECT COUNT(*) FROM users WHERE avaliable=true", nativeQuery = true)
    List<UserBook> findAllActiveUsers(Pageable pageable);


    @Query(value = "SELECT * FROM users WHERE available=false",
            countQuery = "SELECT COUNT(*) FROM users WHERE available=false", nativeQuery = true)
    List<UserBook> findAllNonActiveUsers(Pageable pageable);

//    List<UserBook> findAllUsersByActiveTrue(Pageable pageable);


    //samir nechta kitob oldi
//    @Query(value = "select count (u.book.id) from UserBook  u where u.user.id = ? and u.active = true ")
//    int countBook(@Param("user_id") Long userId);
//    @Query(value = "SELECT COUNT(ub.book_id) FROM user_book ub INNER JOIN book b ON ub.book_id = b.id WHERE ub.user_id = ? AND ub.active = true", nativeQuery = true)
    int countByUserIdAndBookStatusTrue(@Param("user_id") Long userId);


}
