DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS product;

CREATE TABLE user
(
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(100),
   password VARCHAR(100),
   PRIMARY KEY(id)
);

CREATE TABLE product
(
   code INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(100),
   description VARCHAR(100),
   price VARCHAR(100),
   evaluation VARCHAR(100),
   PRIMARY KEY(code)
);