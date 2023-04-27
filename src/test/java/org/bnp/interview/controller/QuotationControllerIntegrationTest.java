package org.bnp.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bnp.interview.mapper.QuotationMapperTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class QuotationControllerIntegrationTest {

	private static final String QUOTATION_ENDPOINT = "/quotations";
	private static final String QUOTATION_CONTENT_TYPE = "application/vnd.bnp.interview.quotation-v1+json";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void givenQuotation_whenCreateQuotation_thenReturnQuotation() throws Exception {
		mockMvc.perform(
						post(QUOTATION_ENDPOINT)
								.contentType(QUOTATION_CONTENT_TYPE)
								.content(objectMapper.writeValueAsString(QuotationMapperTest.createQuotation())))
				.andDo(print())
				.andExpect(content().contentType(MediaType.valueOf(QUOTATION_CONTENT_TYPE)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.beginingOfInsurance").value("2018-01-26T12:07:50.305Z"))
				.andExpect(jsonPath("$.insuredAmount").value("16.585"))
				.andExpect(jsonPath("$.dateOfSigningMortgage").value("2019-02-26T12:07:50.305Z"))
				.andExpect(jsonPath("$.customer").isNotEmpty())
				.andExpect(jsonPath("$.customer.id").exists())
				.andExpect(jsonPath("$.customer.firstName").value("Roman"))
				.andExpect(jsonPath("$.customer.lastName").value("Machytka"))
				.andExpect(jsonPath("$.customer.middleName").value("Pepa"))
				.andExpect(jsonPath("$.customer.email").value("roman@gmail.com"))
				.andExpect(jsonPath("$.customer.phoneNumber").value("+420987654321"))
				.andExpect(jsonPath("$.customer.birthDate").value("1988-06-01"));
	}
}
