-- CulturaTech Bogotá
-- Promueve a ADMIN un usuario que ya exista en la tabla usuario.
-- Reemplaza el correo por el del usuario que debe administrar el sistema.

SET @correo_admin = 'CORREO_DEL_ADMIN_AQUI';

UPDATE usuario
SET rol = 'ADMIN'
WHERE correo = @correo_admin;

SELECT id_usuario, nombres, apellidos, correo, rol
FROM usuario
WHERE correo = @correo_admin;
