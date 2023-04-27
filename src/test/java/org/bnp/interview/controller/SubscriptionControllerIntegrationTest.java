package org.bnp.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bnp.interview.mapper.SubscriptionMapperTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
class SubscriptionControllerIntegrationTest {

	private static final String SUBSCRIPTION_ENDPOINT = "/subscriptions";
	private static final String SUBSCRIPTION_CONTENT_TYPE = "application/vnd.bnp.interview.subscription-v1+json";
	private static final String INVALID_SUBSCRIPTION_ID = "cd3caf22-65d5-44da-8b3c-cf442b43c279";
	private static final String SUBSCRIPTION_ID = "416d38c6-8842-49a2-bf55-4a14c01415f9";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void givenSubscription_whenCreateSubscription_thenReturnSubscription() throws Exception {
		mockMvc.perform(
						post(SUBSCRIPTION_ENDPOINT)
								.contentType(SUBSCRIPTION_CONTENT_TYPE)
								.content(objectMapper.writeValueAsString(SubscriptionMapperTest.createSubscription())))
				.andDo(print())
				.andExpect(content().contentType(MediaType.valueOf(SUBSCRIPTION_CONTENT_TYPE)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.startDate").value("2018-01-26T12:07:50.305Z"))
				.andExpect(jsonPath("$.validUntil").value("2019-02-26T12:07:50.305Z"))
				.andExpect(jsonPath("$.quotation.id").exists())
				.andExpect(jsonPath("$.quotation.beginingOfInsurance").value("2018-01-26T12:07:50.305Z"))
				.andExpect(jsonPath("$.quotation.insuredAmount").value("16.585"))
				.andExpect(jsonPath("$.quotation.dateOfSigningMortgage").value("2019-02-26T12:07:50.305Z"))
				.andExpect(jsonPath("$.quotation.customer").isNotEmpty())
				.andExpect(jsonPath("$.quotation.customer.id").exists())
				.andExpect(jsonPath("$.quotation.customer.firstName").value("Roman"))
				.andExpect(jsonPath("$.quotation.customer.lastName").value("Machytka"))
				.andExpect(jsonPath("$.quotation.customer.middleName").value("Pepa"))
				.andExpect(jsonPath("$.quotation.customer.email").value("roman@gmail.com"))
				.andExpect(jsonPath("$.quotation.customer.phoneNumber").value("+420987654321"))
				.andExpect(jsonPath("$.quotation.customer.birthDate").value("1988-06-01"));
	}

	@Test
	void givenSubscriptionId_whenGetSubscription_thenReturnSubscription() throws Exception {
		mockMvc.perform(
						get(SUBSCRIPTION_ENDPOINT + "/" + SUBSCRIPTION_ID)
				)
				.andDo(print())
				.andExpect(content().contentType(MediaType.valueOf(SUBSCRIPTION_CONTENT_TYPE)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.startDate").value("2022-04-26T14:07:50.305+02:00"))
				.andExpect(jsonPath("$.validUntil").value("2022-04-26T14:07:50.305+02:00"))
				.andExpect(jsonPath("$.quotation.id").exists())
				.andExpect(jsonPath("$.quotation.beginingOfInsurance").value("2023-04-26T14:07:50.305+02:00"))
				.andExpect(jsonPath("$.quotation.insuredAmount").value("123.65"))
				.andExpect(jsonPath("$.quotation.dateOfSigningMortgage").value("2022-04-26T14:07:50.305+02:00"))
				.andExpect(jsonPath("$.quotation.customer").isNotEmpty())
				.andExpect(jsonPath("$.quotation.customer.id").exists())
				.andExpect(jsonPath("$.quotation.customer.firstName").value("firstName"))
				.andExpect(jsonPath("$.quotation.customer.lastName").value("lastName"))
				.andExpect(jsonPath("$.quotation.customer.middleName").value("middleName"))
				.andExpect(jsonPath("$.quotation.customer.email").value("email"))
				.andExpect(jsonPath("$.quotation.customer.phoneNumber").value("phoneNumber"))
				.andExpect(jsonPath("$.quotation.customer.birthDate").value("2023-04-26"));
	}

	@Test
	void givenUnknownSubscriptionId_whenGetSubscription_thenReturnNotFound() throws Exception {
		mockMvc.perform(
						get(SUBSCRIPTION_ENDPOINT + "/" + INVALID_SUBSCRIPTION_ID)
				)
				.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.timestamp").exists())
				.andExpect(jsonPath("$.status").value("404"))
				.andExpect(jsonPath("$.message").value("Subscription with id 'cd3caf22-65d5-44da-8b3c-cf442b43c279' not found."))
				.andExpect(jsonPath("$.path").value("/subscriptions/cd3caf22-65d5-44da-8b3c-cf442b43c279"));
	}
}
