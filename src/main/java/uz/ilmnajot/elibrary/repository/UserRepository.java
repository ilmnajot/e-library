package uz.ilmnajot.elibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.elibrary.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findUserByEmailOrId(String email, Long id);

    Optional<User> findUserByIdAndAvailableTrue(Long id);
}
