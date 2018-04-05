package org.funfactorium.funfacts;

import org.funfactorium.funfacts.topics.Topic;
import org.funfactorium.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FunFact {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    private User author;

    @ManyToMany
    private Set<Topic> topicSet = new HashSet<>();

    private double rating;

    public FunFact() {
    }

    public FunFact(String title, String description, User author, Topic topic, double rating) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.topicSet.add(topic);
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Topic> getTopic() {
        return topicSet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
