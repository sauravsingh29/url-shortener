CREATE SCHEMA url;

CREATE TABLE url.account (
  acc_id  VARCHAR(50) NOT NULL PRIMARY KEY,
  acc_pwd VARCHAR(20) NOT NULL,
);

CREATE TABLE url.registration (
  id            INT         NOT NULL IDENTITY (1, 1) PRIMARY KEY,
  acc_id        VARCHAR(20) NOT NULL,
  short_url     NVARCHAR    NULL,
  url           NVARCHAR    NOT NULL,
  redirect_type INT         NOT NULL,
  visit_count   BIGINT      NULL,
  FOREIGN KEY (acc_id) REFERENCES account (acc_id),
  UNIQUE (acc_id, url)
);