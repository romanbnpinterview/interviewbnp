package org.bnp.interview.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class Subscription {
	UUID id;
	Quotation quotation;
	ZonedDateTime startDate;
	ZonedDateTime validUntil;
}
