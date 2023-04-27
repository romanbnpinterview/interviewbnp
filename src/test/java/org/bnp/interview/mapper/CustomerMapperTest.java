package org.bnp.interview.mapper;

import org.bnp.interview.model.Customer;
import org.bnp.interview.repository.entity.CustomerEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerMapperTest {

	private static final String FIRST_NAME = "Roman";
	private static final String MIDDLE_NAME = "Pepa";
	private static final String LAST_NAME = "Machytka";
	private static final String BIRTH_DATE = "1988-06-01";
	private static final String EMAIL = "roman@gmail.com";
	private static final String PHONE = "+420987654321";
	private static final UUID ID = UUID.randomUUID();
	private static final UUID BUSINESS_ID = UUID.randomUUID();

	private final CustomerMapper toTest = new CustomerMapper();

	@Test
	void givenNull_whenMapToCustomer_thenReturnOptionalEmpty() {
		final Optional<Customer> result = toTest.mapToCustomer(null);
		assertThat(result).isNotPresent();
	}

	@Test
	void givenCustomerEntity_whenMapToCustomer_thenReturnOptionalPresent() {
		final Optional<Customer> result = toTest.mapToCustomer(createCustomerEntity());
		assertThat(result).isPresent();
		assertCustomer(result.get());
	}

	@Test
	void givenNull_whenMapToCustomerEntity_thenReturnOptionalEmpty() {
		final Optional<CustomerEntity> result = toTest.mapToCustomerEntity(null);
		assertThat(result).isNotPresent();
	}

	@Test
	void givenCustomer_whenMapToCustomerEntity_thenReturnOptionalPresent() {
		final Optional<CustomerEntity> result = toTest.mapToCustomerEntity(createCustomer());
		assertThat(result).isPresent();
		assertCustomerEntity(result.get());
	}

	public static void assertCustomer(Customer customer) {
		assertThat(customer.getId()).isEqualTo(BUSINESS_ID);
		assertThat(customer.getFirstName()).isEqualTo(FIRST_NAME);
		assertThat(customer.getMiddleName()).isEqualTo(MIDDLE_NAME);
		assertThat(customer.getLastName()).isEqualTo(LAST_NAME);
		assertThat(customer.getEmail()).isEqualTo(EMAIL);
		assertThat(customer.getPhoneNumber()).isEqualTo(PHONE);
		assertThat(customer.getBirthDate()).isEqualTo(BIRTH_DATE);
	}

	public static void assertCustomerEntity(CustomerEntity customerEntity) {
		assertThat(customerEntity.getId()).isNull();
		assertThat(customerEntity.getBusinessId()).isNotNull();
		assertThat(customerEntity.getFirstName()).isEqualTo(FIRST_NAME);
		assertThat(customerEntity.getMiddleName()).isEqualTo(MIDDLE_NAME);
		assertThat(customerEntity.getLastName()).isEqualTo(LAST_NAME);
		assertThat(customerEntity.getEmail()).isEqualTo(EMAIL);
		assertThat(customerEntity.getPhoneNumber()).isEqualTo(PHONE);
		assertThat(customerEntity.getBirthDate()).isEqualTo(BIRTH_DATE);
	}

	static CustomerEntity createCustomerEntity() {
		return CustomerEntity.builder()
				.id(ID)
				.businessId(BUSINESS_ID)
				.firstName(FIRST_NAME)
				.middleName(MIDDLE_NAME)
				.lastName(LAST_NAME)
				.email(EMAIL)
				.phoneNumber(PHONE)
				.birthDate(LocalDate.parse(BIRTH_DATE))
				.build();
	}

	public static Customer createCustomer() {
		return Customer.builder()
				.id(BUSINESS_ID)
				.firstName(FIRST_NAME)
				.middleName(MIDDLE_NAME)
				.lastName(LAST_NAME)
				.email(EMAIL)
				.phoneNumber(PHONE)
				.birthDate(LocalDate.parse(BIRTH_DATE))
				.build();
	}
}
