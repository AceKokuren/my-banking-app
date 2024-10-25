DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction
(
    transaction_id       INTEGER UNIQUE NOT NULL AUTO_INCREMENT,
    transaction_date     DATE,
    transaction_vendor   VARCHAR(255),
    transaction_type     VARCHAR(255),
    transaction_amount   DECIMAL,
    transaction_category VARCHAR(255),
    primary key (transaction_id)
);