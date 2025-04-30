package com.demo.tree.repo;

import com.demo.tree.model.StatementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepo extends JpaRepository<StatementEntity, Long> {
}
