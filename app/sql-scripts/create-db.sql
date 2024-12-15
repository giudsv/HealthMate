-- Room
CREATE TABLE IF NOT EXISTS room_master_table (
    id INTEGER PRIMARY KEY,
    identity_hash TEXT
);
INSERT OR REPLACE INTO room_master_table
    (id, identity_hash)
VALUES
    (42, '48184fdcef074c7b52f674d69f369191');

-- Medico
CREATE TABLE IF NOT EXISTS Medico (
    `id` INTEGER NOT NULL,
    `nome` TEXT,
    `cognome` TEXT,
    `CF` TEXT,
    `data_nascita` TEXT,
    `cellulare` TEXT,
    `email` TEXT,
    `username` TEXT,
    `password` TEXT,
    `studio` TEXT,
    `numero_albo` TEXT,
    PRIMARY KEY(`id`)
);

-- Paziente
CREATE TABLE IF NOT EXISTS Paziente (
    `id` INTEGER NOT NULL,
    `nome` TEXT,
    `cognome` TEXT,
    `CF` TEXT,
    `data_nascita` TEXT,
    `cellulare` TEXT,
    `email` TEXT,
    `username` TEXT,
    `password` TEXT,
    `sesso` TEXT,
    `email_tutore` TEXT,
    `medico_id` INTEGER NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`medico_id`) REFERENCES `Medico`(`id`)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);
CREATE INDEX IF NOT EXISTS `index_Paziente_medico_id` ON Paziente (`medico_id`);

-- Visita
CREATE TABLE IF NOT EXISTS Visita (
    `id` INTEGER NOT NULL,
    `data` TEXT,
    `luogo` TEXT,
    `paziente_id` INTEGER NOT NULL,
    `medico_id` INTEGER NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`paziente_id`) REFERENCES `Paziente`(`id`)
        ON UPDATE CASCADE
        ON DELETE SET NULL ,
    FOREIGN KEY(`medico_id`) REFERENCES `Medico`(`id`)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS `index_Visita_paziente_id` ON Visita (`paziente_id`);
CREATE INDEX IF NOT EXISTS `index_Visita_medico_id` ON Visita (`medico_id`);