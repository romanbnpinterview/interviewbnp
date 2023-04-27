package org.bnp.interview.mapper;

import org.bnp.interview.model.Customer;
import org.bnp.interview.repository.entity.CustomerEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerMapper {

	public Optional<Customer> mapToCustomer(CustomerEntity customerEntity) {
		Customer result = null;
		if (customerEntity != null) {
			result = Customer.builder()
					.id(customerEntity.getBusinessId())
					.firstName(customerEntity.getFirstName())
					.lastName(customerEntity.getLastName())
					.middleName(customerEntity.getMiddleName())
					.email(customerEntity.getEmail())
					.phoneNumber(customerEntity.getPhoneNumber())
					.birthDate(customerEntity.getBirthDate())
					.build();
		}
		return Optional.ofNullable(result);
	}

	public Optional<CustomerEntity> mapToCustomerEntity(Customer customer) {
		CustomerEntity result = null;
		if (customer != null) {
			result = CustomerEntity.builder()
					.businessId(UUID.randomUUID())
					.firstName(customer.getFirstName())
					.lastName(customer.getLastName())
					.middleName(customer.getMiddleName())
					.email(customer.getEmail())
					.phoneNumber(customer.getPhoneNumber())
					.birthDate(customer.getBirthDate())
					.build();
		}
		return Optional.ofNullable(result);
	}
}
