package org.bnp.interview.service;

import org.bnp.interview.mapper.CustomerMapper;
import org.bnp.interview.mapper.QuotationMapper;
import org.bnp.interview.mapper.QuotationMapperTest;
import org.bnp.interview.model.Quotation;
import org.bnp.interview.repository.QuotationRepository;
import org.bnp.interview.repository.entity.QuotationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class QuotationServiceTest {
	private static final UUID BUSINESS_ID = UUID.randomUUID();

	private QuotationService toTest;
	@Mock
	private QuotationRepository quotationRepositoryMock;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		toTest = new QuotationService(new QuotationMapper(new CustomerMapper()), quotationRepositoryMock);
	}

	@Test
	void givenValidParams_whenCreateQuotation_thenReturnQuotation() {
		QuotationEntity savedQuotationEntity = QuotationEntity.builder().businessId(BUSINESS_ID).build();

		when(quotationRepositoryMock.save(any(QuotationEntity.class))).thenReturn(savedQuotationEntity);

		final Quotation result = toTest.createQuotation(QuotationMapperTest.createQuotation());

		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(BUSINESS_ID);

		final ArgumentCaptor<QuotationEntity> quotationEntityCaptor = ArgumentCaptor.forClass(QuotationEntity.class);
		verify(quotationRepositoryMock, times(1)).save(quotationEntityCaptor.capture());

		QuotationMapperTest.assertQuotationEntity(quotationEntityCaptor.getValue());
	}

	@Test
	void givenNullQuotation_whenCreateQuotation_thenThrowIllegalArgumentException() {

		final IllegalArgumentException result = (IllegalArgumentException) catchException(() -> toTest.createQuotation(null));

		assertThat(result).isNotNull();
		assertThat(result.getMessage()).isEqualTo("Quotation cannot be null.");

		final ArgumentCaptor<QuotationEntity> quotationEntityCaptor = ArgumentCaptor.forClass(QuotationEntity.class);
		verify(quotationRepositoryMock, times(0)).save(quotationEntityCaptor.capture());
	}
}
