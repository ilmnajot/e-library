package uz.ilmnajot.elibrary.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.ilmnajot.elibrary.base.BaseEntity;
import uz.ilmnajot.elibrary.enums.BookStatus;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBook extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    private LocalDateTime returnedDate;

//    @Enumerated(EnumType.STRING)
//    private BookStatus bookStatus;
//

    private boolean bookStatus;



}
