DROP TABLE IF EXISTS wallet;

CREATE TABLE wallet
(
    id        int NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    wallet_id uuid   NOT NULL,
    balance   numeric,
    created   timestamp DEFAULT CURRENT_TIMESTAMP(),
    updated   timestamp DEFAULT CURRENT_TIMESTAMP()
);

COMMENT ON TABLE wallet IS 'Holds persons wallet and funding';

COMMENT ON COLUMN wallet.wallet_id IS 'UUID of wallet';

CREATE UNIQUE INDEX IF NOT EXISTS wallet_idx ON wallet (wallet_id);

