insert into customer (id, business_id, first_name, last_name, middle_name, email, phone_number, birth_date)
values
('ed428252-7ae8-457b-881a-afed5e49b40c', '60b9c139-b35d-4379-bb0f-140ac0b84aad', 'firstName', 'lastName', 'middleName', 'email', 'phoneNumber', '2023-04-26');

insert into quotation (id, business_id, customer_id, begining_of_insurance, insured_amount, date_of_signing_mortgage)
values
('64e582ce-26bf-4c48-a032-2b920fee340f', 'aa133e4b-5f80-4e0d-ba69-ba55fe753042', 'ed428252-7ae8-457b-881a-afed5e49b40c', '2023-04-26 14:07:50.305+02', '123.65', '2022-04-26 14:07:50.305+02');

insert into subscription (id, business_id, quotation_id, start_date, valid_until)
values
('2da10f63-638f-48d2-9fcd-2e1f55be78d6', '416d38c6-8842-49a2-bf55-4a14c01415f9', '64e582ce-26bf-4c48-a032-2b920fee340f', '2022-04-26 14:07:50.305+02', '2022-04-26 14:07:50.305+02');