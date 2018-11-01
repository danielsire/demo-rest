package com.rest.demoRest.repository;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonRepositoryTest {

	private static final String PERSON_ENDPOINT = "/api/people";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void test1ShouldReturnAll() throws Exception {
		mockMvc.perform(get(PERSON_ENDPOINT)
		.contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("_embedded.people", hasSize(1)));
		
        /*MvcResult res =  mockMvc.perform(get(PERSON_ENDPOINT)
        		.contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        
		System.out.println("RES----------" + res.getResponse().getContentAsString());
		*/
	}
	
	@Test
	public void test2ShouldReturnOneById() throws Exception {
		mockMvc.perform(get(PERSON_ENDPOINT+"/1")
		.contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name", is("Daniel")));
	}
	
	@Test
	public void test3ShouldReturnSave() throws Exception {
		mockMvc.perform(post(PERSON_ENDPOINT)
		.content("{\n" + 
				"	\"name\": \"Fulano\",\n" + 
				"	\"email\": \"fulano@mail.com\"\n" + 
				"}")
		.contentType(APPLICATION_JSON))
        .andExpect(status().is(201));
	}
	
	@Test
	public void test4ShouldReturnUpdate() throws Exception {
		mockMvc.perform(put(PERSON_ENDPOINT+"/1")
		.content("{\n" + 
				"	\"name\": \"Fulano2\",\n" + 
				"	\"email\": \"fulano@mail.com\"\n" + 
				"}")
		.contentType(APPLICATION_JSON))
        .andExpect(status().is(204));
		
		mockMvc.perform(get(PERSON_ENDPOINT+"/1")
				.contentType(APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("name", is("Fulano2")));
	}
	
	@Test
	public void test5ShouldReturnFindByOrderByCreatedAtDesc() throws Exception {
		mockMvc.perform(get(PERSON_ENDPOINT+"/search/findByOrderByCreatedAtDesc")
		.contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("_embedded.people", hasSize(2)))
        .andExpect(jsonPath("_embedded.people[0].name", is("Fulano2")))
        .andExpect(jsonPath("_embedded.people[1].name", is("Fulano")));
	}
	
	@Test
	public void test6ShouldReturnFindByName() throws Exception {
		mockMvc.perform(get(PERSON_ENDPOINT+"/search/findByName?name=Fulano2")
		.contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("_embedded.people", hasSize(1)))
        .andExpect(jsonPath("_embedded.people[0].name", is("Fulano2")));
	}
	
	@Test
	public void test7ShouldReturnDelete() throws Exception {
		mockMvc.perform(delete(PERSON_ENDPOINT+"/1")
		.contentType(APPLICATION_JSON))
        .andExpect(status().is(204));
	}
	
}
