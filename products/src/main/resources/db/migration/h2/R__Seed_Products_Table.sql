-- Repeatable script to seed the Products table

MERGE INTO Products KEY(product_id)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'Product A', 'Description A', 10.0, 'Category A', 100, CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
    ('550e8400-e29b-41d4-a716-446655440001', 'Product B', 'Description B', 20.0, 'Category B', 200, CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
    ('550e8400-e29b-41d4-a716-446655440002', 'Product C', 'Description C', 30.0, 'Category C', 300, CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');

-- Add more seed data as necessary