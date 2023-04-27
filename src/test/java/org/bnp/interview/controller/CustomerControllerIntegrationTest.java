package org.bnp.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bnp.interview.mapper.CustomerMapperTest;
import org.bnp.interview.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/scenario/populate.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scenario/cleanup.sql")
class CustomerControllerIntegrationTest {

	private static final String CUSTOMER_ENDPOINT = "/customers";
	private static final String CUSTOMER_CONTENT_TYPE = "application/vnd.bnp.interview.customer-v1+json";
	private static final String INVALID_CUSTOMER_ID = "cd3caf22-65d5-44da-8b3c-cf442b43c279";
	private static final String CUSTOMER_ID = "60b9c139-b35d-4379-bb0f-140ac0b84aad";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void givenCustomer_whenUpdateCustomer_thenReturnCustomer() throws Exception {
		mockMvc.perform(
						put(CUSTOMER_ENDPOINT + "/" + CUSTOMER_ID)
								.contentType(CUSTOMER_CONTENT_TYPE)
								.content(objectMapper.writeValueAsString(CustomerMapperTest.createCustomer()))
				)
				.andDo(print())
				.andExpect(content().contentType(MediaType.valueOf(CUSTOMER_CONTENT_TYPE)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.id").value("60b9c139-b35d-4379-bb0f-140ac0b84aad"))
				.andExpect(jsonPath("$.firstName").value("Roman"))
				.andExpect(jsonPath("$.lastName").value("Machytka"))
				.andExpect(jsonPath("$.middleName").value("Pepa"))
				.andExpect(jsonPath("$.email").value("roman@gmail.com"))
				.andExpect(jsonPath("$.phoneNumber").value("+420987654321"))
				.andExpect(jsonPath("$.birthDate").value("1988-06-01"));
	}

	@Test
	void givenCustomerWithNullValues_whenUpdateCustomer_thenReturnCustomer() throws Exception {
		mockMvc.perform(
						put(CUSTOMER_ENDPOINT + "/" + CUSTOMER_ID)
								.contentType(CUSTOMER_CONTENT_TYPE)
								.content(objectMapper.writeValueAsString(Customer.builder().build()))
				)
				.andDo(print())
				.andExpect(content().contentType(MediaType.valueOf(CUSTOMER_CONTENT_TYPE)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.id").value("60b9c139-b35d-4379-bb0f-140ac0b84aad"))
				.andExpect(jsonPath("$.firstName").doesNotExist())
				.andExpect(jsonPath("$.lastName").doesNotExist())
				.andExpect(jsonPath("$.middleName").doesNotExist())
				.andExpect(jsonPath("$.email").doesNotExist())
				.andExpect(jsonPath("$.phoneNumber").doesNotExist())
				.andExpect(jsonPath("$.birthDate").doesNotExist());
	}

	@Test
	void givenUnknownCustomerId_whenUpdateCustomer_thenReturnNotFound() throws Exception {
		mockMvc.perform(
						put(CUSTOMER_ENDPOINT + "/" + INVALID_CUSTOMER_ID)
								.contentType(CUSTOMER_CONTENT_TYPE)
								.content(objectMapper.writeValueAsString(CustomerMapperTest.createCustomer()))
				)
				.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.timestamp").exists())
				.andExpect(jsonPath("$.status").value("404"))
				.andExpect(jsonPath("$.message").value("Customer with id 'cd3caf22-65d5-44da-8b3c-cf442b43c279' not found."))
				.andExpect(jsonPath("$.path").value("/customers/cd3caf22-65d5-44da-8b3c-cf442b43c279"));
	}
}
