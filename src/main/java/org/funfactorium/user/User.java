package org.funfactorium.user;

import org.funfactorium.funfacts.FunFact;
import org.funfactorium.security.email.ValidateEmail;
import org.funfactorium.security.password.PasswordMatches;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@PasswordMatches
@SequenceGenerator(name = "seq", initialValue = 2, allocationSize = 2)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Transient
    private String matchingPassword;

    @Column(nullable = false)
    @ValidateEmail
    private String email;

    @OneToMany(mappedBy = "author")
    private Set<FunFact> authoredFunFacts = new HashSet<>();

    public User() {
    }

    public User(String userName, String password, String email, String matchingPassword) {
        this.userName = userName;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<FunFact> getAuthoredFunFacts() {
        return authoredFunFacts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
