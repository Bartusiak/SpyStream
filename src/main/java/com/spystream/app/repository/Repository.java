package com.spystream.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface Repository extends MongoRepository<User, String> {

    public User findByEmail(String email);
    public boolean existsByEmail (String email);
}
