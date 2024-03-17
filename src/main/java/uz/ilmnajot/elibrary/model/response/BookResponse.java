package uz.ilmnajot.elibrary.model.response;

import lombok.Data;
import uz.ilmnajot.elibrary.enums.BookStatus;
import uz.ilmnajot.elibrary.enums.Category;

@Data
public class BookResponse {

    private Long id;

    private String bookName;

    private String authorName;

    private int isbn;

    private int bookQuantity;

    private boolean available;

    private Category category;
//    private int bookCode;


//    private boolean bookStatus;
//
//    @OneToOne
//    private User user;
//
//    @Column(name = "user_id", insertable = false, updatable = false)
//    private Long userId;


}

