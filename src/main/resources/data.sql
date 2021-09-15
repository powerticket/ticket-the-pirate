INSERT INTO product_delivery (closing, type) VALUES ('fast', '12:00');
INSERT INTO product (delivery_id, description, name, created_at, updated_at) VALUES (1, '노르웨이산 연어 300g, 500g, 반마리 필렛', '노르웨이산 연어', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO product_option (name, price, stock) VALUES ('생연어 몸통살 300g', 10000, 99);
INSERT INTO product_option (name, price, stock) VALUES ('생연어 몸통살 500g', 17000, 99);
INSERT INTO product_options (product_id, options_id) VALUES (1, 1);
INSERT INTO product_options (product_id, options_id) VALUES (1, 2);

INSERT INTO product_delivery (closing, type) VALUES ('regular', '18:00');
INSERT INTO product (delivery_id, description, name, created_at, updated_at) VALUES (1, '산지직송 완도 전복 1kg (7미~60미)', '완도전복', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO product_option (name, price, stock) VALUES ('대 7~8미', 50000, 99);
INSERT INTO product_option (name, price, stock) VALUES ('중 14~15미', 34000, 99);
INSERT INTO product_option (name, price, stock) VALUES ('소 50~60미', 20000, 99);
INSERT INTO product_options (product_id, options_id) VALUES (2, 3);
INSERT INTO product_options (product_id, options_id) VALUES (2, 4);
INSERT INTO product_options (product_id, options_id) VALUES (2, 5);
