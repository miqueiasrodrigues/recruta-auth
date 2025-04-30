CREATE TABLE IF NOT EXISTS auth.tb_usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    sobrenome VARCHAR(150) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    senha TEXT NOT NULL,
    ativo BOOLEAN NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    data_modificacao TIMESTAMP,
    criado_por VARCHAR(255) NOT NULL,
    modificado_por VARCHAR(255)
);
