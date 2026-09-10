# CulturaTech Bogotá — Perfil y administración

Esta versión incorpora:
- Configuración de perfil para consultar, actualizar y eliminar la propia cuenta.
- Panel administrativo protegido por rol ADMIN.
- CRUD administrativo de usuarios, eventos, categorías y lugares.
- Formularios JSP/HTML con GET para consultas y POST para operaciones de escritura.
- Acceso a MySQL mediante JDBC y PreparedStatement.
- Los cambios de eventos, categorías y lugares se leen desde la base de datos y se reflejan en las interfaces que consumen los Servlets.

## Crear el primer administrador

Registra primero un usuario desde el flujo normal de registro y luego ejecuta
`database/crear_administrador.sql` en MySQL, reemplazando
`CORREO_DEL_ADMIN_AQUI` por el correo del usuario que será administrador.

No se incluyen contraseñas reales de la base de datos en esta versión.
Configura `src/java/util/ConexionBD.java` únicamente en tu entorno local.



## Autoría y trazabilidad

**Autor:** Carlos Rodrigo Ortegón
**Correo:** crortegon37@hotmail.com  
**Proyecto:**  SENA ADSO-3235898 CulturaTech Bogotá  
**Año:** 2026