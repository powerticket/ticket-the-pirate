CREATE TABLE product (id bigint generated BY DEFAULT AS IDENTITY, created_at TIMESTAMP, description VARCHAR(255), name VARCHAR(255), updated_at TIMESTAMP, delivery_id bigint, PRIMARY KEY (id));
CREATE TABLE product_options (product_id bigint NOT NULL, options_id bigint NOT NULL);
CREATE TABLE product_delivery (id bigint generated BY DEFAULT AS IDENTITY, closing varchar(255), type VARCHAR(255), PRIMARY KEY (id));
CREATE TABLE product_option (id bigint generated BY DEFAULT AS IDENTITY, name VARCHAR(255), price bigint, stock bigint, PRIMARY KEY (id));

ALTER TABLE product_options ADD CONSTRAINT UK_9358l2q11cd47a05fj7yxw4ex UNIQUE (options_id);
ALTER TABLE product ADD CONSTRAINT FKgrh0dciajlqutjtdb40tjg73p FOREIGN KEY (delivery_id) REFERENCES product_delivery;
ALTER TABLE product_options ADD CONSTRAINT FK8p4cqivp1mx4r0gh75epqb9ql FOREIGN KEY (options_id) REFERENCES product_option;
ALTER TABLE product_options ADD CONSTRAINT FKh8jbibj0kf2549ogbap8x8612 FOREIGN KEY (product_id) REFERENCES product;