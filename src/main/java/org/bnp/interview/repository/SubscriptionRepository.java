package org.bnp.interview.repository;

import org.bnp.interview.repository.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, UUID> {
	Optional<SubscriptionEntity> findByBusinessId(UUID subscriptionId);
}
