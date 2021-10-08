package com.example.springbootcrudapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springbootcrudapp.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	Optional<Book> findByIsbn(String isbn);
	List<Book> findByTitleContaining(String title);
	List<Book> findByPublished(boolean published);
}
