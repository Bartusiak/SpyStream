package com.spystream.app.repository;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private String _id;

    private String email;
    private String password;
    private String token;

    public User(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public static void add(Repository repository, String email, String password, String token){
        if(repository.existsByEmail(email)){
            throw new IllegalArgumentException("This email is already used.");
        }
        repository.save(new User(email, password, token));
    }
}
