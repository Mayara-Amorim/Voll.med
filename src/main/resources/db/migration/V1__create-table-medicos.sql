CREATE TABLE medicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    crm VARCHAR(6) NOT NULL,
    especialidade VARCHAR(100) NOT NULL,
    logradouro VARCHAR(20) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    complemento VARCHAR(100),
    numero INT(20),
    uf CHAR(2) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);
