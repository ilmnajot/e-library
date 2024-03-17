package uz.ilmnajot.elibrary.model.request;

import jakarta.persistence.Column;
import lombok.Data;
import uz.ilmnajot.elibrary.enums.BookStatus;
import uz.ilmnajot.elibrary.enums.Category;

@Data
public class BookRequest {

    private String bookName;

    private String authorName;

    private int isbn;

    @Column(nullable = false)
    private int bookQuantity;

    private boolean available=true;

    private Category category;
//    private boolean delete;

//    private int bookCode;


//    private boolean existing;
//    @OneToOne
//    private User user;
//
//    @Column(name = "user_id", insertable = false, updatable = false)
//    private Long userId;
}
