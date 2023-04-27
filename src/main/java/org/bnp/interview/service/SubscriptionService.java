package org.bnp.interview.service;

import lombok.RequiredArgsConstructor;
import org.bnp.interview.exception.EntityNotFoundException;
import org.bnp.interview.mapper.SubscriptionMapper;
import org.bnp.interview.model.Subscription;
import org.bnp.interview.repository.SubscriptionRepository;
import org.bnp.interview.repository.entity.SubscriptionEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionService {

	private final SubscriptionMapper subscriptionMapper;
	private final SubscriptionRepository subscriptionRepository;

	public Subscription createSubscription(Subscription subscription) {
		if (subscription == null) {
			throw new IllegalArgumentException("Subscription cannot be null.");
		}
		final SubscriptionEntity entityToSave = subscriptionMapper.mapToSubscriptionEntity(subscription);
		final SubscriptionEntity savedEntity = subscriptionRepository.save(entityToSave);
		return subscriptionMapper.mapToSubscription(savedEntity);
	}

	public Subscription getSubscription(UUID subscriptionId) {
		final Optional<SubscriptionEntity> subscriptionEntity = subscriptionRepository.findByBusinessId(subscriptionId);
		if (subscriptionEntity.isPresent()) {
			return subscriptionMapper.mapToSubscription(subscriptionEntity.get());
		} else {
			throw new EntityNotFoundException("Subscription with id '" + subscriptionId + "' not found.");
		}
	}
}
