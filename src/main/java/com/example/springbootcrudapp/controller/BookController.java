package com.example.springbootcrudapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootcrudapp.model.Book;
import com.example.springbootcrudapp.repository.BookRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class BookController {

	@Autowired
	BookRepository bookRepository;

	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllTutorials(@RequestParam(required = false) String title) {
		try {
			List<Book> books = new ArrayList<Book>();

			if (title == null) {
				bookRepository.findAll().forEach(books::add);
			} else {
				bookRepository.findByTitleContaining(title).forEach(books::add);
			}

			if (books.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
		Optional<Book> bookData = bookRepository.findById(id);

		if (bookData.isPresent()) {
			return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/books/published")
	public ResponseEntity<List<Book>> findByPublished() {
		try {
			List<Book> books = bookRepository.findByPublished(true);

			if (books.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/books/isbn/{isbn}")
	public ResponseEntity<Book> getBookById(@PathVariable("isbn") String isbn) {
		Optional<Book> tutorialData = bookRepository.findByIsbn(isbn);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/books")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		try {
			Book _book = bookRepository.save(new Book(book.getTitle(), book.getIsbn(), book.getDescription(), false));
			return new ResponseEntity<>(_book, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/books/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
		Optional<Book> tutorialData = bookRepository.findById(id);

		if (tutorialData.isPresent()) {
			Book _book = tutorialData.get();
			_book.setTitle(book.getTitle());
			_book.setDescription(book.getDescription());
			_book.setPublished(book.isPublished());
			return new ResponseEntity<>(bookRepository.save(_book), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/books/isbn/{isbn}")
	public ResponseEntity<Book> updateBookByIsbn(@PathVariable("isbn") String isbn, @RequestBody Book book) {
		Optional<Book> bookData = bookRepository.findByIsbn(isbn);

		if (bookData.isPresent()) {
			Book _book = bookData.get();
			_book.setTitle(book.getTitle());
			_book.setDescription(book.getDescription());
			_book.setPublished(book.isPublished());
			return new ResponseEntity<>(bookRepository.save(_book), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") long id) {
		try {
			bookRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/tutorials/isbn/{isbn}")
	public ResponseEntity<HttpStatus> deleteBookByIsbn(@PathVariable("id") long id) {
		try {
			bookRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/books")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			bookRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
