package org.funfactorium.funfacts.topics;

import org.funfactorium.funfacts.FunFact;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Topic {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToMany
    private Set<FunFact> funFactSet = new HashSet<>();

    public Topic() {
    }

    public Topic(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<FunFact> getFunFacts() {
        return funFactSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
