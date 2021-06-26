package com.qasoftdev.dscatalog.repositories;



import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.qasoftdev.dscatalog.entities.Product;
import com.qasoftdev.dscatalog.services.exceptions.ResourceNotFoundException;

@DataJpaTest
public class ProductRepositoryTests {
	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private Long noExistingId;
	
	@BeforeEach
	void setup() throws Exception {
		existingId = 1L;
		noExistingId = 1000L;
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
				
		repository.deleteById(existingId);
		
		Optional<Product> result = repository.findById(existingId);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			
			repository.deleteById(noExistingId );
		});
	}
	
	@Test
	public void findByIdShouldFindOjectWhenIfExists() {
		
		Optional<Product> result = repository.findById(existingId);
		
		Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void indByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
Optional<Product> result = repository.findById(noExistingId);
		
		Assertions.assertFalse(result.isPresent());
		
//		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
//			
//			Optional<Product> result = repository.findById(noExistingId);
//		});
	}
	
	
}
