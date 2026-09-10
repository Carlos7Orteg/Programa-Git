<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Usuario u=(Usuario)request.getAttribute("usuario");
%>

<%!
    String esc(String v){
        return v == null ? "" : v.replace("&","&amp;")
            .replace("<","&lt;")
            .replace(">","&gt;")
            .replace("\"","&quot;");
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,400,0,0" rel="stylesheet">
<title>Configuración de perfil - CulturaTech Bogotá</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/estilos.css">
</head>
<body class="perfil-page">
    <div class="login-background perfil-background">
        <div class="perfil-overlay"></div>
        <main class="perfil-wrapper">
            <a href="<%=request.getContextPath()%>/pages/home.html" class="perfil-back">
                <span class="material-symbols-outlined">arrow_back</span>Volver al inicio
            </a>
            <section class="perfil-card">
                <div class="perfil-header">
                    <div class="perfil-logo">
                        <img src="<%=request.getContextPath()%>/assets/images/logo_CulturaTechBG.png" alt="CulturaTech Bogotá">
                    </div>

                    <div class="perfil-heading">
                        <span class="perfil-eyebrow">MI CUENTA</span>
                        <h1>Configuración de perfil</h1>
                        <p>Administra tu información personal y los datos asociados a tu cuenta.</p>
                    </div>

                </div>
                <% if(request.getAttribute("mensaje") != null){ %>
                    <div class="perfil-alert perfil-alert-success">
                        <span class="material-symbols-outlined">check_circle</span>
                        <span><%=esc((String)request.getAttribute("mensaje"))%></span>
                    </div>

                <% } %>

                <% if(request.getAttribute("error") != null){ %>

                    <div class="perfil-alert perfil-alert-error">
                        <span class="material-symbols-outlined">error</span>
                        <span><%=esc((String)request.getAttribute("error"))%></span>
                    </div>

                <% } %>

                <form action="<%=request.getContextPath()%>/PerfilServlet" method="post" class="perfil-form">
                    <input type="hidden" name="accion" value="actualizar">
                    
                    <div class="perfil-section">
                        <div class="perfil-section-title">
                            <span class="material-symbols-outlined">person</span>
                            <div>
                                <h2>Datos personales</h2>
                                <p>Información asociada a tu cuenta.</p>
                            </div>
                        </div>

                        <div class="perfil-grid">
                            <div class="perfil-field">
                                <label for="nombres">Nombres</label>
                                <div class="perfil-input-wrap">
                                    <span class="material-symbols-outlined">person</span>
                                    <input id="nombres" name="nombres" type="text" value="<%=esc(u.getNombres())%>" required>
                                </div>
                            </div>
                            <div class="perfil-field">
                                <label for="apellidos">Apellidos</label>
                                <div class="perfil-input-wrap">
                                    <span class="material-symbols-outlined">badge</span>
                                    <input id="apellidos" name="apellidos" type="text" value="<%=esc(u.getApellidos())%>" required>
                                </div>

                            </div>
                            <div class="perfil-field">
                                <label for="documento">Documento</label>
                                <div class="perfil-input-wrap">
                                    <span class="material-symbols-outlined">id_card</span>
                                    <input id="documento" name="documento" type="text" value="<%=esc(u.getDocumento())%>" required>
                                </div>
                            </div>

                            <div class="perfil-field">
                                <label for="fechaNacimiento">Fecha de nacimiento</label>
                                <div class="perfil-input-wrap">
                                    <span class="material-symbols-outlined">calendar_today</span>
                                    <input id="fechaNacimiento" name="fechaNacimiento" type="date" value="<%=u.getFechaNacimiento()%>" required>
                                </div>
                            </div>
                            <div class="perfil-field perfil-field-full">
                                <label for="correo">Correo electrónico</label>
                                <div class="perfil-input-wrap">
                                    <span class="material-symbols-outlined">mail</span>
                                    <input id="correo" name="correo" type="email" value="<%=esc(u.getCorreo())%>" required>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="perfil-divider"></div>
                    
                    <div class="perfil-section">
                        <div class="perfil-section-title">
                            <span class="material-symbols-outlined">lock</span>
                            <div>
                                <h2>Seguridad</h2>
                                <p>Actualiza tu contraseña cuando lo necesites.</p>
                            </div>
                        </div>

                        <div class="perfil-field perfil-field-full">
                            <label for="contrasena">Nueva contraseña</label>
                            <div class="perfil-input-wrap">
                                <span class="material-symbols-outlined">lock</span>
                                <input id="contrasena" name="contrasena" type="password" minlength="8" placeholder="Dejar vacío para conservar la actual">
                            </div>
                            <small>Tu contraseña actual nunca se muestra.</small>
                        </div>
                    </div>

                    <div class="perfil-actions">
                        <button type="submit" class="perfil-btn perfil-btn-primary">
                            <span class="material-symbols-outlined">save</span>
                            Guardar cambios
                        </button>
                    </div>
                </form>

                <div class="perfil-divider"></div>
                <section class="perfil-danger">
                    <div class="perfil-section-title perfil-danger-title">
                        <span class="material-symbols-outlined">warning</span>
                        <div>
                            <h2>Zona de cuenta</h2>
                            <p>Esta acción es permanente.</p>
                        </div>
                    </div>
                    <div class="perfil-danger-content">
                        <div>
                            <strong>Eliminar cuenta</strong>
                            <p>Elimina tu cuenta y los registros personales relacionados. Esta acción no se puede deshacer.</p>
                        </div>
                        
                        <form action="<%=request.getContextPath()%>/PerfilServlet" method="post" onsubmit="return confirm('¿Deseas eliminar definitivamente tu cuenta?');">
                            <input type="hidden" name="accion" value="eliminar">
                            <button type="submit" class="perfil-btn perfil-btn-danger">
                                <span class="material-symbols-outlined">delete</span>
                                Eliminar cuenta
                            </button>
                        </form>
                    </div>
                </section>
            </section>
        </main>
    </div>

</body>
</html>
