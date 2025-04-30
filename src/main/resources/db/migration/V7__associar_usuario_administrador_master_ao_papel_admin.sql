INSERT INTO auth.tb_usuario_papel (usuario_id, papel_id)
SELECT u.id, p.id
FROM auth.tb_usuarios u
JOIN auth.tb_papeis p ON p.descricao = 'ADMIN'
WHERE u.email = 'admin@admin.com'
  AND NOT EXISTS (
      SELECT 1 FROM auth.tb_usuario_papel up
      WHERE up.usuario_id = u.id AND up.papel_id = p.id
  );