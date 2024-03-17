package uz.ilmnajot.elibrary.model.response;

import lombok.Data;
import uz.ilmnajot.elibrary.enums.SchoolName;

@Data
public class UserResponse {


    private Long id;

    private String firstName;

    private String lastName;

    private String grade;

    private String username;

//    private SchoolName school;

//    private Long bookId;


    private boolean available;
}
