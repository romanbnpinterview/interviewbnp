package org.bnp.interview.service;

import lombok.RequiredArgsConstructor;
import org.bnp.interview.exception.EntityNotFoundException;
import org.bnp.interview.mapper.CustomerMapper;
import org.bnp.interview.model.Customer;
import org.bnp.interview.repository.CustomerRepository;
import org.bnp.interview.repository.entity.CustomerEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerMapper customerMapper;
	private final CustomerRepository customerRepository;

	public Customer updateCustomer(UUID customerId, Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("Customer cannot be null.");
		}
		final Optional<CustomerEntity> entityToUpdate = customerRepository.findByBusinessId(customerId);
		if (entityToUpdate.isPresent()) {
			final CustomerEntity updatedEntity = updateCustomer(entityToUpdate.get(), customer);
			final CustomerEntity savedEntity = customerRepository.save(updatedEntity);
			return customerMapper.mapToCustomer(savedEntity).orElseThrow(IllegalStateException::new);
		} else {
			throw new EntityNotFoundException("Customer with id '" + customerId + "' not found.");
		}
	}

	private CustomerEntity updateCustomer(CustomerEntity entityToUpdate, Customer customer) {
		return entityToUpdate.toBuilder()
				.firstName(customer.getFirstName())
				.middleName(customer.getMiddleName())
				.lastName(customer.getLastName())
				.email(customer.getEmail())
				.phoneNumber(customer.getPhoneNumber())
				.birthDate(customer.getBirthDate())
				.build();
	}
}
