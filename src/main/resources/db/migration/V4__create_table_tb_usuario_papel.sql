CREATE TABLE IF NOT EXISTS auth.tb_usuario_papel (
    usuario_id BIGINT NOT NULL,
    papel_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, papel_id),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES auth.tb_usuarios (id),
    CONSTRAINT fk_papel FOREIGN KEY (papel_id) REFERENCES auth.tb_papeis (id)
);