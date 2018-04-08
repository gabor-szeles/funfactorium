package org.funfactorium.funfacts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunFactRepository extends JpaRepository<FunFact, Long> {

    List<FunFact> findByTopicSetId(Long id);

    FunFact findById(Long id);

    @Query(value = "select max(f.id) from FunFact f")
    Long findMaxId();

    List<FunFact> findAllByTopicSet_name(String topicName);
}
