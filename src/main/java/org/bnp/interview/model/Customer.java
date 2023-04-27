package org.bnp.interview.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class Customer {
	UUID id;
	String firstName;
	String lastName;
	String middleName;
	String email;
	String phoneNumber;
	LocalDate birthDate;
}
