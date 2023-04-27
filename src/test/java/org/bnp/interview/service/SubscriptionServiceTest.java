package org.bnp.interview.service;

import org.bnp.interview.exception.EntityNotFoundException;
import org.bnp.interview.mapper.CustomerMapper;
import org.bnp.interview.mapper.QuotationMapper;
import org.bnp.interview.mapper.SubscriptionMapper;
import org.bnp.interview.mapper.SubscriptionMapperTest;
import org.bnp.interview.model.Subscription;
import org.bnp.interview.repository.SubscriptionRepository;
import org.bnp.interview.repository.entity.SubscriptionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SubscriptionServiceTest {
	private static final UUID BUSINESS_ID = UUID.randomUUID();

	private SubscriptionService toTest;
	@Mock
	private SubscriptionRepository subscriptionRepositoryMock;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		toTest = new SubscriptionService(new SubscriptionMapper(new QuotationMapper(new CustomerMapper())), subscriptionRepositoryMock);
	}

	@Test
	void givenValidParams_whenCreateSubscription_thenReturnSubscription() {
		SubscriptionEntity savedSubscriptionEntity = SubscriptionEntity.builder().businessId(BUSINESS_ID).build();

		when(subscriptionRepositoryMock.save(any(SubscriptionEntity.class))).thenReturn(savedSubscriptionEntity);

		final Subscription result = toTest.createSubscription(SubscriptionMapperTest.createSubscription());

		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(BUSINESS_ID);

		final ArgumentCaptor<SubscriptionEntity> subscriptionEntityCaptor = ArgumentCaptor.forClass(SubscriptionEntity.class);
		verify(subscriptionRepositoryMock, times(1)).save(subscriptionEntityCaptor.capture());

		SubscriptionMapperTest.assertSubscriptionEntity(subscriptionEntityCaptor.getValue());
	}

	@Test
	void givenNullSubscription_whenCreateQuotation_thenThrowIllegalArgumentException() {

		final IllegalArgumentException result = (IllegalArgumentException) catchException(() -> toTest.createSubscription(null));

		assertThat(result).isNotNull();
		assertThat(result.getMessage()).isEqualTo("Subscription cannot be null.");

		final ArgumentCaptor<SubscriptionEntity> subscriptionEntityCaptor = ArgumentCaptor.forClass(SubscriptionEntity.class);
		verify(subscriptionRepositoryMock, times(0)).save(subscriptionEntityCaptor.capture());
	}

	@Test
	void givenValidParams_whenGetSubscription_thenReturnSubscription() {
		SubscriptionEntity savedSubscriptionEntity = SubscriptionEntity.builder().businessId(BUSINESS_ID).build();

		when(subscriptionRepositoryMock.findByBusinessId(BUSINESS_ID)).thenReturn(Optional.of(savedSubscriptionEntity));

		final Subscription result = toTest.getSubscription(BUSINESS_ID);

		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(BUSINESS_ID);
	}

	@Test
	void givenUnknownSubscriptionId_whenGetSubscription_thenThrowEntityNotFoundException() {

		when(subscriptionRepositoryMock.findByBusinessId(BUSINESS_ID)).thenReturn(Optional.empty());
		final UUID invalidSubscriptionId = UUID.randomUUID();
		final EntityNotFoundException result = (EntityNotFoundException) catchException(() -> toTest.getSubscription(invalidSubscriptionId));

		assertThat(result).isNotNull();
		assertThat(result.getMessage()).isEqualTo("Subscription with id '" + invalidSubscriptionId + "' not found.");
	}
}
