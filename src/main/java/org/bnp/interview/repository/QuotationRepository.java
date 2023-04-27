package org.bnp.interview.repository;

import org.bnp.interview.repository.entity.QuotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuotationRepository extends JpaRepository<QuotationEntity, UUID> {
}
