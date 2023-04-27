package org.bnp.interview.service;

import org.bnp.interview.exception.EntityNotFoundException;
import org.bnp.interview.mapper.CustomerMapper;
import org.bnp.interview.mapper.CustomerMapperTest;
import org.bnp.interview.model.Customer;
import org.bnp.interview.repository.CustomerRepository;
import org.bnp.interview.repository.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerServiceTest {
	private static final UUID BUSINESS_ID = UUID.randomUUID();

	private CustomerService toTest;
	@Mock
	private CustomerRepository customerRepositoryMock;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		toTest = new CustomerService(new CustomerMapper(), customerRepositoryMock);
	}

	@Test
	void givenValidParams_whenUpdateCustomer_thenReturnCustomer() {
		CustomerEntity savedCustomerEntity = CustomerEntity.builder().businessId(BUSINESS_ID).build();

		when(customerRepositoryMock.findByBusinessId(BUSINESS_ID)).thenReturn(Optional.of(savedCustomerEntity));
		when(customerRepositoryMock.save(any(CustomerEntity.class))).thenReturn(savedCustomerEntity);

		final Customer result = toTest.updateCustomer(BUSINESS_ID, CustomerMapperTest.createCustomer());

		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(BUSINESS_ID);

		final ArgumentCaptor<CustomerEntity> customerEntityCaptor = ArgumentCaptor.forClass(CustomerEntity.class);
		verify(customerRepositoryMock, times(1)).save(customerEntityCaptor.capture());

		CustomerMapperTest.assertCustomerEntity(customerEntityCaptor.getValue());
	}

	@Test
	void givenNullCustomer_whenUpdateCustomer_thenThrowIllegalArgumentException() {

		final IllegalArgumentException result = (IllegalArgumentException) catchException(() -> toTest.updateCustomer(BUSINESS_ID, null));

		assertThat(result).isNotNull();
		assertThat(result.getMessage()).isEqualTo("Customer cannot be null.");

		final ArgumentCaptor<CustomerEntity> customerEntityCaptor = ArgumentCaptor.forClass(CustomerEntity.class);
		verify(customerRepositoryMock, times(0)).save(customerEntityCaptor.capture());
	}

	@Test
	void givenUnknownCustomerId_whenUpdateCustomer_thenThrowEntityNotFoundException() {

		when(customerRepositoryMock.findByBusinessId(BUSINESS_ID)).thenReturn(Optional.empty());
		final UUID invalidCustomerId = UUID.randomUUID();
		final EntityNotFoundException result = (EntityNotFoundException) catchException(() -> toTest.updateCustomer(invalidCustomerId, CustomerMapperTest.createCustomer()));

		assertThat(result).isNotNull();
		assertThat(result.getMessage()).isEqualTo("Customer with id '" + invalidCustomerId + "' not found.");

		final ArgumentCaptor<CustomerEntity> customerEntityCaptor = ArgumentCaptor.forClass(CustomerEntity.class);
		verify(customerRepositoryMock, times(0)).save(customerEntityCaptor.capture());
	}
}
