package com.rest.demoRest.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.rest.demoRest.model.Person;

import io.swagger.annotations.Api;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
@Api("DEMO REST")
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

	List<Person> findByOrderByCreatedAtDesc();
	
	List<Person> findByName(String name);

	
	
}
