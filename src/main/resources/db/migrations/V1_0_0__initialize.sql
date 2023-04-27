CREATE
    TABLE
        customer(
            id          VARCHAR(36) NOT NULL,
            business_id VARCHAR(36) NOT NULL,
            first_name VARCHAR(100),
            last_name VARCHAR(100),
            middle_name VARCHAR(100),
            email VARCHAR(255),
            phone_number VARCHAR(255),
            birth_date     DATE,
            PRIMARY KEY (id)
        );
CREATE INDEX customer_business_id_idx ON customer (business_id);

CREATE
    TABLE
        quotation(
            id          VARCHAR(36) NOT NULL,
            business_id VARCHAR(36) NOT NULL,
            customer_id VARCHAR(36),
            begining_of_insurance TIMESTAMP WITH TIME ZONE NOT NULL,
            insured_amount DECIMAL(10,2) NOT NULL,
            date_of_signing_mortgage TIMESTAMP WITH TIME ZONE NOT NULL,
            PRIMARY KEY (id)
        );
CREATE INDEX quotation_business_id_idx ON quotation (business_id);

ALTER TABLE `quotation` ADD CONSTRAINT `fk_quotation_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`);

CREATE
    TABLE
        subscription(
            id          VARCHAR(36) NOT NULL,
            business_id VARCHAR(36) NOT NULL,
            quotation_id VARCHAR(36),
            start_date TIMESTAMP WITH TIME ZONE NOT NULL,
            valid_until TIMESTAMP WITH TIME ZONE NOT NULL,
            PRIMARY KEY (id)
        );
CREATE INDEX subscription_business_id_idx ON subscription (business_id);

ALTER TABLE `subscription` ADD CONSTRAINT `fk_subscription_quotation_id` FOREIGN KEY (`quotation_id`) REFERENCES `quotation` (`id`);