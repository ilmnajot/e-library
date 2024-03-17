package uz.ilmnajot.elibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.elibrary.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
