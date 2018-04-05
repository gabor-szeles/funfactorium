package org.funfactorium.funfacts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunFactRepository extends JpaRepository<FunFact, Long> {
}
