INSERT INTO auth.tb_papeis (descricao)
SELECT 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM auth.tb_papeis WHERE descricao = 'ADMIN'
);

INSERT INTO auth.tb_papeis (descricao)
SELECT 'CANDIDATO'
WHERE NOT EXISTS (
    SELECT 1 FROM auth.tb_papeis WHERE descricao = 'CANDIDATO'
);