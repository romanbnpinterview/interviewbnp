package org.bnp.interview.controller;

import lombok.RequiredArgsConstructor;
import org.bnp.interview.model.Subscription;
import org.bnp.interview.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

	private static final String SUBSCRIPTION_ENDPOINT = "/subscriptions";

	private final SubscriptionService subscriptionService;

	@PostMapping(path = SUBSCRIPTION_ENDPOINT, produces = "application/vnd.bnp.interview.subscription-v1+json", consumes = "application/vnd.bnp.interview.subscription-v1+json")
	ResponseEntity<Subscription> createSubscriptions(@RequestBody Subscription subscription) {
		final Subscription createdSubscription = subscriptionService.createSubscription(subscription);
		return ResponseEntity.created(URI.create(SUBSCRIPTION_ENDPOINT + "/" + createdSubscription.getId())).body(createdSubscription);
	}

	@GetMapping(path = SUBSCRIPTION_ENDPOINT + "/{subscriptionId}", produces = "application/vnd.bnp.interview.subscription-v1+json")
	ResponseEntity<Subscription> getSubscriptions(@PathVariable(value = "subscriptionId") UUID subscriptionId) {
		final Subscription subscription = subscriptionService.getSubscription(subscriptionId);
		return ResponseEntity.ok(subscription);
	}
}
