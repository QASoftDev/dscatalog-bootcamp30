package com.qasoftdev.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.qasoftdev.dscatalog.repositories.ProductRepository;
import com.qasoftdev.dscatalog.services.exceptions.DatabaseException;
import com.qasoftdev.dscatalog.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	@Mock
	private ProductRepository repository;
	
	private long existingId;
	private Long noExistingId;
	private Long dependentId;
	
	@BeforeEach
	void setup() throws Exception {
		existingId = 1L;
		noExistingId = 1000L;
		dependentId = 4L;
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(noExistingId);
		
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
	}
	
	
	

	@Test
	public void deleteShouldThrowsDatabaseExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(DatabaseException.class,() -> {
			service.delete(dependentId);
		});
	
		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
	
	}
	
	
	@Test
	public void deleteShouldThrowsEmptyResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class,() -> {
			service.delete(noExistingId);
		});
	
		Mockito.verify(repository, Mockito.times(1)).deleteById(noExistingId);
	
	}
	
	@Test
	public void deleteShouldNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
	
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	
	}
	
}
