package tienthuan.jwtauthenbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tienthuan.jwtauthenbackend.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("FROM Role r WHERE r.code = ?1")
    Optional<Role> findByCode(String code);

}
