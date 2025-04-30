INSERT INTO auth.tb_usuarios (nome, sobrenome, cpf, email, senha, ativo, data_criacao, data_modificacao, criado_por, modificado_por)
SELECT 'Administrador', 'Master', '00000000000', 'admin@admin.com', '$2a$10$LSjC96VRizF4Bv3M5hgCte9wzOAwq2gfDiB1cIPd1qJBc4zdQG4fm', true, now(), now(), 'NOVO_USUARIO', 'NOVO_USUARIO'
WHERE NOT EXISTS (
    SELECT 1 FROM auth.tb_usuarios WHERE email = 'admin@admin.com'
);