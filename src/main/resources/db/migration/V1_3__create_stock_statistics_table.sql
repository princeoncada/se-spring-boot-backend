CREATE TABLE tbl_stock_statistics(
     id VARCHAR(36) NOT NULL PRIMARY KEY,
     ticker VARCHAR(255) UNIQUE NOT NULL,
     stock_id VARCHAR(36) NOT NULL REFERENCES tbl_stocks(id) ON DELETE CASCADE,
     trailing_pe DOUBLE NOT NULL,
     price_sales DOUBLE NOT NULL,
     price_book DOUBLE NOT NULL,
     enterprise_value_editda DOUBLE NOT NULL,
     return_on_equity DOUBLE NOT NULL,
     quarterly_revenue_growth DOUBLE NOT NULL,
     quarterly_earnings_growth DOUBLE NOT NULL,
     total_debt_equity DOUBLE NOT NULL,
     forward_annual_dividend_yield DOUBLE NOT NULL,
     trailing_annual_dividend_yield DOUBLE NOT NULL,
     payout_ratio DOUBLE NOT NULL,
     interest_coverage_ratio DOUBLE NOT NULL,
     operating_cash_flow_net_income_ratio DOUBLE NOT NULL,
     free_cash_flow_conversion DOUBLE NOT NULL,
     debt_coverage_ratio DOUBLE NOT NULL,
     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);