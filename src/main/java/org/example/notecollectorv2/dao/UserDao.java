package org.example.notecollectorv2.dao;

import org.example.notecollectorv2.entity.impl.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//Sets as a dao layer object or an interface. Meta annotated as a component
public interface UserDao extends JpaRepository<UserEntity, String> {
}
