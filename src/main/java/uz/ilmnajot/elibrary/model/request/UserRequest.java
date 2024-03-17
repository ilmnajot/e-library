package uz.ilmnajot.elibrary.model.request;

import lombok.Data;
import uz.ilmnajot.elibrary.enums.SchoolName;

@Data
public class UserRequest {

    private String firstName;

    private String lastName;

    private String grade;

    private String email;

    private SchoolName school;

    private Long bookId;

    private boolean available;
}
