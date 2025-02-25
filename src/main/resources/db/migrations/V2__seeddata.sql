INSERT INTO GROUPCHAIN(id, group_name) values(1, 'Hiberus');
INSERT INTO PRICE_RATE(id, price_rate_name) values(1, 'Price Rate 1');
INSERT INTO PRICE_RATE(id, price_rate_name) values(2, 'Price Rate 2');
INSERT INTO PRICE_RATE(id, price_rate_name) values(3, 'Price Rate 3');
INSERT INTO PRICE_RATE(id, price_rate_name) values(4, 'Price Rate 4');

INSERT INTO OFFER(offer_id, brand_id, start_date, end_date, price_list_id, product_part_number, priority, price, currency_iso) VALUES
    (1, 1, '2020-06-14T00:00:00Z', '2020-06-30T23:59:59Z', 1, '001002', 0, 35.50, 'EUR'),
    (2, 1, '2020-06-14T15:00:00Z', '2020-06-14T18:30:00Z', 1, '001002', 1, 25.45, 'EUR'),
    (3, 1, '2020-06-15T00:00:00Z', '2020-06-15T11:00:00Z', 1, '001002', 1, 30.50, 'EUR'),
    (4, 1, '2020-06-15T16:00:00Z', '2020-12-31T23:59:59Z', 1, '001002', 1, 34.95, 'EUR');