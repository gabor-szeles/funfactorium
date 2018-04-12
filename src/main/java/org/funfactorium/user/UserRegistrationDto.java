package org.funfactorium.user;

import org.funfactorium.security.email.ValidateEmail;
import org.funfactorium.security.password.PasswordMatches;

@PasswordMatches
public class UserRegistrationDto {

    private String userName;

    private String password;

    private String matchingPassword;

    @ValidateEmail
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
