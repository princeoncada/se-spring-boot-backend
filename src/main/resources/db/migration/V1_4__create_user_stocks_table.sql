CREATE TABLE tbl_user_stocks(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    ticker VARCHAR(255) NOT NULL,
    user_id VARCHAR(36) NOT NULL REFERENCES tbl_users(id),
    stock_id VARCHAR(36) NOT NULL REFERENCES tbl_stocks(id)
);