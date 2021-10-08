package com.example.springbootcrudapp.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springbootcrudapp.model.Book;

@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {
	Optional<Book> findByIsbn(String isbn);

	List<Book> findByTitleContaining(String title);

	List<Book> findByPublished(boolean published);

	// @Modifying
	// @Query("delete from Book u where u.isbn = ?1")
	List<Book> deleteByIsbn(String isbn);

	Long removeByIsbn(String isbn);
}
