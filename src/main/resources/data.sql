insert into customer (last_name, first_name, country, city, address, zip, phone_number, email)
values ('Kovács', 'Zoltán', 'Hungary', 'Szeged', 'Ifjúság 14', '7000', '+36701234567', 'kovacszoltan@gmail.com');

insert into account (account_number, account_type, account_status, balance, begin_balance, begin_time, customer_id)
values ('12345678-98765432-00000000', 'CHECKING', 'ACTIVE', 10000.0, 10000.0, current_timestamp, 1);

insert into account (account_number, account_type, account_status, balance, begin_balance, begin_time, customer_id)
values ('12345678-98765432-23459876', 'CHECKING', 'ACTIVE', 12000.0, 12000.0, current_timestamp, 1);