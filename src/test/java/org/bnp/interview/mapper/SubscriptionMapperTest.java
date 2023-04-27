package org.bnp.interview.mapper;

import org.bnp.interview.model.Subscription;
import org.bnp.interview.repository.entity.SubscriptionEntity;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bnp.interview.mapper.QuotationMapperTest.assertQuotation;
import static org.bnp.interview.mapper.QuotationMapperTest.assertQuotationEntity;

public class SubscriptionMapperTest {

	private static final String START_DATE = "2018-01-26T12:07:50.305Z";
	private static final String VALID_UNTIL = "2019-02-26T12:07:50.305Z";
	private static final UUID ID = UUID.randomUUID();
	private static final UUID BUSINESS_ID = UUID.randomUUID();

	private final SubscriptionMapper toTest = new SubscriptionMapper(new QuotationMapper(new CustomerMapper()));

	@Test
	void givenSubscriptionEntity_whenMapToQuotation_thenReturnSubscription() {
		final Subscription result = toTest.mapToSubscription(createSubscriptionEntity());
		assertThat(result).isNotNull();
		assertSubscription(result);
	}

	@Test
	void givenSubscription_whenMapToSubscriptionEntity_thenReturnSubscriptionEntity() {
		final SubscriptionEntity result = toTest.mapToSubscriptionEntity(createSubscription());
		assertThat(result).isNotNull();
		assertSubscriptionEntity(result);
	}

	public static void assertSubscription(Subscription subscription) {
		assertThat(subscription.getId()).isEqualTo(BUSINESS_ID);
		assertThat(subscription.getStartDate()).isEqualTo(START_DATE);
		assertThat(subscription.getValidUntil()).isEqualTo(VALID_UNTIL);
		assertQuotation(subscription.getQuotation());
	}

	public static void assertSubscriptionEntity(SubscriptionEntity subscriptionEntity) {
		assertThat(subscriptionEntity.getId()).isNull();
		assertThat(subscriptionEntity.getBusinessId()).isNotNull();
		assertThat(subscriptionEntity.getStartDate()).isEqualTo(START_DATE);
		assertThat(subscriptionEntity.getValidUntil()).isEqualTo(VALID_UNTIL);
		assertQuotationEntity(subscriptionEntity.getQuotation());
	}

	static SubscriptionEntity createSubscriptionEntity() {
		return SubscriptionEntity.builder()
				.id(ID)
				.businessId(BUSINESS_ID)
				.startDate(ZonedDateTime.parse(START_DATE))
				.validUntil(ZonedDateTime.parse(VALID_UNTIL))
				.quotation(QuotationMapperTest.createQuotationEntity())
				.build();
	}

	public static Subscription createSubscription() {
		return Subscription.builder()
				.id(BUSINESS_ID)
				.startDate(ZonedDateTime.parse(START_DATE))
				.validUntil(ZonedDateTime.parse(VALID_UNTIL))
				.quotation(QuotationMapperTest.createQuotation())
				.build();
	}
}
