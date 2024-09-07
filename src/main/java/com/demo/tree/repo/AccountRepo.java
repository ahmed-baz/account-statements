package com.demo.tree.repo;

import com.demo.tree.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, Long> {
}
