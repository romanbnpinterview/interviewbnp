package org.bnp.interview.mapper;

import lombok.RequiredArgsConstructor;
import org.bnp.interview.model.Subscription;
import org.bnp.interview.repository.entity.SubscriptionEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SubscriptionMapper {

	private final QuotationMapper quotationMapper;

	public Subscription mapToSubscription(SubscriptionEntity subscriptionEntity) {
		return Subscription.builder()
				.id(subscriptionEntity.getBusinessId())
				.startDate(subscriptionEntity.getStartDate())
				.validUntil(subscriptionEntity.getValidUntil())
				.quotation(quotationMapper.mapToQuotation(subscriptionEntity.getQuotation()).orElse(null))
				.build();
	}

	public SubscriptionEntity mapToSubscriptionEntity(Subscription subscription) {
		return SubscriptionEntity.builder()
				.businessId(UUID.randomUUID())
				.startDate(subscription.getStartDate())
				.validUntil(subscription.getValidUntil())
				.quotation(quotationMapper.mapToQuotationEntity(subscription.getQuotation()).orElse(null))
				.build();
	}
}
