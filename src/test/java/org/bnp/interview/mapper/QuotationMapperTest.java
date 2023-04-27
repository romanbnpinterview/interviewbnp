package org.bnp.interview.mapper;

import org.bnp.interview.model.Quotation;
import org.bnp.interview.repository.entity.QuotationEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bnp.interview.mapper.CustomerMapperTest.assertCustomer;
import static org.bnp.interview.mapper.CustomerMapperTest.assertCustomerEntity;

public class QuotationMapperTest {

	private static final String BEGINING_OF_INSURANCE = "2018-01-26T12:07:50.305Z";
	private static final String DATE_OF_SIGNING_MORTGAGE = "2019-02-26T12:07:50.305Z";
	private static final BigDecimal INSURED_AMOUNT = BigDecimal.valueOf(16.585);
	private static final UUID ID = UUID.randomUUID();
	private static final UUID BUSINESS_ID = UUID.randomUUID();

	private final QuotationMapper toTest = new QuotationMapper(new CustomerMapper());

	@Test
	void givenNull_whenMapToQuotation_thenReturnOptionalEmpty() {
		final Optional<Quotation> result = toTest.mapToQuotation(null);
		assertThat(result).isNotPresent();
	}

	@Test
	void givenQuotationEntity_whenMapToQuotation_thenReturnOptionalPresent() {
		final Optional<Quotation> result = toTest.mapToQuotation(createQuotationEntity());
		assertThat(result).isPresent();
		assertQuotation(result.get());
	}

	@Test
	void givenNull_whenMapToQuotationEntity_thenReturnOptionalEmpty() {
		final Optional<QuotationEntity> result = toTest.mapToQuotationEntity(null);
		assertThat(result).isNotPresent();
	}

	@Test
	void givenQuotation_whenMapToQuotationEntity_thenReturnOptionalPresent() {
		final Optional<QuotationEntity> result = toTest.mapToQuotationEntity(createQuotation());
		assertThat(result).isPresent();
		assertQuotationEntity(result.get());
	}

	public static void assertQuotation(Quotation quotation) {
		assertThat(quotation.getId()).isEqualTo(BUSINESS_ID);
		assertThat(quotation.getBeginingOfInsurance()).isEqualTo(BEGINING_OF_INSURANCE);
		assertThat(quotation.getInsuredAmount()).isEqualTo(INSURED_AMOUNT);
		assertThat(quotation.getDateOfSigningMortgage()).isEqualTo(DATE_OF_SIGNING_MORTGAGE);
		assertCustomer(quotation.getCustomer());
	}

	public static void assertQuotationEntity(QuotationEntity quotationEntity) {
		assertThat(quotationEntity.getId()).isNull();
		assertThat(quotationEntity.getBusinessId()).isNotNull();
		assertThat(quotationEntity.getBeginingOfInsurance()).isEqualTo(BEGINING_OF_INSURANCE);
		assertThat(quotationEntity.getInsuredAmount()).isEqualTo(INSURED_AMOUNT);
		assertThat(quotationEntity.getDateOfSigningMortgage()).isEqualTo(DATE_OF_SIGNING_MORTGAGE);
		assertCustomerEntity(quotationEntity.getCustomer());
	}

	static QuotationEntity createQuotationEntity() {
		return QuotationEntity.builder()
				.id(ID)
				.businessId(BUSINESS_ID)
				.beginingOfInsurance(ZonedDateTime.parse(BEGINING_OF_INSURANCE))
				.insuredAmount(INSURED_AMOUNT)
				.dateOfSigningMortgage(ZonedDateTime.parse(DATE_OF_SIGNING_MORTGAGE))
				.customer(CustomerMapperTest.createCustomerEntity())
				.build();
	}

	public static Quotation createQuotation() {
		return Quotation.builder()
				.id(BUSINESS_ID)
				.beginingOfInsurance(ZonedDateTime.parse(BEGINING_OF_INSURANCE))
				.insuredAmount(INSURED_AMOUNT)
				.dateOfSigningMortgage(ZonedDateTime.parse(DATE_OF_SIGNING_MORTGAGE))
				.customer(CustomerMapperTest.createCustomer())
				.build();
	}
}
