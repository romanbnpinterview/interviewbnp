package org.bnp.interview.service;

import lombok.RequiredArgsConstructor;
import org.bnp.interview.mapper.QuotationMapper;
import org.bnp.interview.model.Quotation;
import org.bnp.interview.repository.QuotationRepository;
import org.bnp.interview.repository.entity.QuotationEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuotationService {

	private final QuotationMapper quotationMapper;
	private final QuotationRepository quotationRepository;

	public Quotation createQuotation(Quotation quotation) {
		if (quotation == null) {
			throw new IllegalArgumentException("Quotation cannot be null.");
		}
		final QuotationEntity entityToSave = quotationMapper.mapToQuotationEntity(quotation).orElseThrow(IllegalStateException::new);
		final QuotationEntity savedEntity = quotationRepository.save(entityToSave);
		return quotationMapper.mapToQuotation(savedEntity).orElseThrow(IllegalStateException::new);
	}
}
