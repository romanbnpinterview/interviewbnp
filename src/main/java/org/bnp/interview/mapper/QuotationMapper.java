package org.bnp.interview.mapper;

import lombok.RequiredArgsConstructor;
import org.bnp.interview.model.Quotation;
import org.bnp.interview.repository.entity.QuotationEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QuotationMapper {

	private final CustomerMapper customerMapper;

	public Optional<Quotation> mapToQuotation(QuotationEntity quotationEntity) {
		Quotation result = null;
		if (quotationEntity != null) {
			result = Quotation.builder()
					.id(quotationEntity.getBusinessId())
					.beginingOfInsurance(quotationEntity.getBeginingOfInsurance())
					.insuredAmount(quotationEntity.getInsuredAmount())
					.dateOfSigningMortgage(quotationEntity.getDateOfSigningMortgage())
					.customer(customerMapper.mapToCustomer(quotationEntity.getCustomer()).orElse(null))
					.build();
		}
		return Optional.ofNullable(result);
	}

	public Optional<QuotationEntity> mapToQuotationEntity(Quotation quotation) {
		QuotationEntity result = null;
		if (quotation != null) {
			result = QuotationEntity.builder()
					.businessId(UUID.randomUUID())
					.beginingOfInsurance(quotation.getBeginingOfInsurance())
					.insuredAmount(quotation.getInsuredAmount())
					.dateOfSigningMortgage(quotation.getDateOfSigningMortgage())
					.customer(customerMapper.mapToCustomerEntity(quotation.getCustomer()).orElse(null))
					.build();
		}
		return Optional.ofNullable(result);
	}
}
