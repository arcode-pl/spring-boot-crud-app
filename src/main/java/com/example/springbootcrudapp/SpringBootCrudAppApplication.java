package com.example.springbootcrudapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCrudAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudAppApplication.class, args);
	}

}

//EntityManager em = emf.createEntityManager();
//em.getTransaction().begin();
//Session session = em.unwrap(Session.class);
// 
//Book b = session.byNaturalId(Book.class).using(Book_.isbn.getName(), “978-0321356680”).load();