package org.funfactorium.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum UserRole {
    USER, ADMIN, BANNED;

    private List<String> authorities;

    static {
        USER.authorities = Arrays.asList(   "ADD_FUNFACT",
                                            "RATE_FUNFACT",
                                            "DELETE_FUNFACT");

        ADMIN.authorities = Arrays.asList(  "ADD_FUNFACT",
                                            "RATE_FUNFACT",
                                            "DELETE_FUNFACT",
                                            "DELETE_ALL_BY_USER",
                                            "BAN_USER");

        BANNED.authorities = new ArrayList<>();
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}

