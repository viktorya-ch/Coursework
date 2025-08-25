CREATE TABLE dynamic_rule (
    id UUID PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_id UUID NOT NULL,
    product_text TEXT NOT NULL,
    rule JSONB NOT NULL
);