package uz.ilmnajot.elibrary.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import uz.ilmnajot.elibrary.base.BaseEntity;
import uz.ilmnajot.elibrary.enums.BookStatus;
import uz.ilmnajot.elibrary.enums.Category;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Book extends BaseEntity {

    private String bookName;

    private String authorName;

    @Column(nullable = false, unique = true)
    private int isbn;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 500, message = "Quantity must not exceed 500")
    private int bookQuantity;

    private boolean available;

    private boolean delete;

//    @Column(nullable = false, unique = true)
//    @Min(value = 5, message = "bookCode must be at least 5")
//    private int bookCode;

    @Enumerated(EnumType.STRING)
    private Category category;

//    @Enumerated(EnumType.STRING)
//    private BookStatus bookStatus;

}
