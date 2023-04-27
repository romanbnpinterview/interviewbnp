package org.bnp.interview.controller;

import lombok.RequiredArgsConstructor;
import org.bnp.interview.model.Customer;
import org.bnp.interview.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@PutMapping(path = "/customers/{customerId}", produces = "application/vnd.bnp.interview.customer-v1+json", consumes = "application/vnd.bnp.interview.customer-v1+json")
	ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {
		return ResponseEntity.ok(customerService.updateCustomer(customerId, customer));
	}
}
