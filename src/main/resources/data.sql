DROP TABLE IF EXISTS consumer_transactions;

CREATE TABLE consumer_transactions (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250),
  transaction_date DATE NOT NULL,
  transaction_amount DOUBLE NOT NULL
);