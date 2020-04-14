package guru.springframework.msscbeerservice.web.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.msscbeerservice.web.model.BeerDto;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetBeerById() throws Exception {

		mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	void testSaveNewBeer() throws Exception {
		
		BeerDto beerDto = BeerDto.builder().build();
		String beerDtoToJason = objectMapper.writeValueAsString(beerDto);
		
		mockMvc.perform(post("/api/v1/beer/" + UUID.randomUUID().toString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoToJason))
				.andExpect(status().isNoContent());
	}

	@Test
	void testUpdateBeerById() throws Exception {

		BeerDto beerDto = BeerDto.builder().build();
		String beerDtoToJason = objectMapper.writeValueAsString(beerDto);
		
		mockMvc.perform(put("/api/v1/beer/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoToJason))
				.andExpect(status().isCreated());
	}

}
