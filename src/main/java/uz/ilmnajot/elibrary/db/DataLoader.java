package uz.ilmnajot.elibrary.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.ilmnajot.elibrary.entity.Book;
import uz.ilmnajot.elibrary.entity.Role;
import uz.ilmnajot.elibrary.entity.User;
import uz.ilmnajot.elibrary.enums.Category;
import uz.ilmnajot.elibrary.enums.RoleName;
import uz.ilmnajot.elibrary.enums.SchoolName;
import uz.ilmnajot.elibrary.repository.BookRepository;
import uz.ilmnajot.elibrary.repository.RoleRepository;
import uz.ilmnajot.elibrary.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String mode;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public DataLoader(BookRepository bookRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (mode.equals("always")) {
         bookRepository.save(
                    Book
                            .builder()
                            .bookName("book name")
                            .authorName("author name")
                            .isbn(1234567891)
                            .bookQuantity(10)
                            .available(true)
//                            .bookCode(54321)
//                            .category(Category.CLASSIC).
                            .build());
            Role admin = roleRepository.save(
                    Role.builder()
                            .name("Admin")
                            .roleName(RoleName.ADMIN)
                            .deleted(false)
                            .build());

            userRepository.save(
                    User.builder()
                            .firstName("John")
                            .lastName("Johnson")
                            .email("email@gmail.com")
                            .grade("grade 6")
                            .password(passwordEncoder.encode("password"))
                            .role(admin)
                            .school(SchoolName.SAMARKAND_PRESIDENTIAL_SCHOOL)
//                            .bookId(1L)
//                            .loanedBook(10)
                            .available(true)
                            .build()
            );
            userRepository.save(
                    User.builder()
                            .firstName("admin firstName here")
                            .lastName("admin lastName here")
                            .email("admin@gmail.com")
                            .grade("6 grade")
                            .password(passwordEncoder.encode("admin"))
//                            .loanedBook(0)
                            .available(true)
                            .role(admin)
                            .school(SchoolName.SAMARKAND_PRESIDENTIAL_SCHOOL)
                            .accountNonExpired(true)
                            .accountNonLocked(true)
                            .credentialsNonExpired(true)
                            .enabled(true)
                            .build()
            );
        }
    }
}
