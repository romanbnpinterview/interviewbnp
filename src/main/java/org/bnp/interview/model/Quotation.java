package org.bnp.interview.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class Quotation {
	UUID id;
	ZonedDateTime beginingOfInsurance;
	BigDecimal insuredAmount;
	ZonedDateTime dateOfSigningMortgage;
	Customer customer;
}
