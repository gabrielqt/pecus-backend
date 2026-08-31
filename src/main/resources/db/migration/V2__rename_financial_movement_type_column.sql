-- ============================================================
-- V2: financial_movement.transaction_type -> financial_movement_type
--     (entity field renamed to financialMovementType, so Hibernate's
--     default naming strategy now expects financial_movement_type)
-- ============================================================

ALTER TABLE financial_movement DROP CONSTRAINT CK_financial_movement_type;
GO

EXEC sp_rename 'financial_movement.transaction_type', 'financial_movement_type', 'COLUMN';
GO

ALTER TABLE financial_movement
    ADD CONSTRAINT CK_financial_movement_type CHECK (financial_movement_type IN ('INCOME', 'EXPENSE'));
GO
