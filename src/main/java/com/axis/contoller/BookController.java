package com.axis.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.entity.Book;
import com.axis.exception.BookNotFoundException;
import com.axis.repository.BookRepository;

@RestController
@RequestMapping("/api/bookstore")
public class BookController {
	
	@Autowired
	private BookRepository bookRepository;
	//get all users
	@GetMapping
	public List<Book> getAllUsers(){
		return this.bookRepository.findAll();
	}
	//get user by id
	@GetMapping("/{id}")
	public Book getUserById(@PathVariable (value = "id") long userId) {
		return this.bookRepository.findById(userId)
				.orElseThrow(() -> new BookNotFoundException("User not found with id:"));
		}
	//create user
	@PostMapping
	public Book createUser(@RequestBody Book book) {
		return this.bookRepository.save(book);
	}
	//update user
	@PutMapping("/{id}")
	public Book updateUser(@RequestBody Book book, @PathVariable("id") long bookId) {
		Book existing = this.bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("Book not found with id:"));
		existing.setBookTitle(book.getBookTitle());
		existing.setBookAuthor(book.getBookAuthor());
		existing.setBookCategory(book.getBookCategory());
		existing.setBookPrice(book.getBookPrice());
		return this.bookRepository.save(existing);
	}
	//delete user by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Book> deleteUser(@PathVariable("id") long bookId){
		Book existingBook = this.bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("User not found with id:"));
		this.bookRepository.delete(existingBook);
		return ResponseEntity.ok().build();
	}
	

}
