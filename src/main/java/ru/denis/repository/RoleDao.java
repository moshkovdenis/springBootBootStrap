package ru.denis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.denis.model.Role;

import java.util.Optional;


@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(String name);
}
