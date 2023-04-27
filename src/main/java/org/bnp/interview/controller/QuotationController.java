package org.bnp.interview.controller;

import lombok.RequiredArgsConstructor;
import org.bnp.interview.model.Customer;
import org.bnp.interview.model.Quotation;
import org.bnp.interview.service.QuotationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class QuotationController {

	private static final String QUOTATION_ENDPOINT = "/quotations";

	private final QuotationService quotationService;

	@PostMapping(path = QUOTATION_ENDPOINT, produces = "application/vnd.bnp.interview.quotation-v1+json", consumes = "application/vnd.bnp.interview.quotation-v1+json")
	ResponseEntity<Quotation> createQuotation(@RequestBody Quotation quotation) {
		final Quotation createdQuotation = quotationService.createQuotation(quotation);
		return ResponseEntity.created(URI.create(QUOTATION_ENDPOINT + "/" + createdQuotation.getId())).body(createdQuotation);
	}
}
