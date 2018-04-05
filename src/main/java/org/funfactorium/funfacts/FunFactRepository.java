package org.funfactorium.funfacts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunFactRepository extends JpaRepository<FunFact, Long> {

    List<FunFact> findByTopicSetId(Long id);
}
