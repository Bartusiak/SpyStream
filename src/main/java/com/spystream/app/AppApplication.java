package com.spystream.app;

import com.spystream.app.repository.Repository;
import com.spystream.app.repository.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AppApplication implements CommandLineRunner {

	@Autowired
	private Repository repository;

	public static void main(String... args) {
		SpringApplication.run(AppApplication.class, args);
	}

	public void run(String... args){
	    repository.deleteAll();

        User.add(repository, "mail@gmail.com", "pass", "token");
        User.add(repository, "mail@gmail.com", "pass", "token");


        System.out.println(repository.findAll());
    }
}
