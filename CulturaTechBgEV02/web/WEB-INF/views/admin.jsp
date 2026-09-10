<%@page import="java.util.*"%>
<%@page import="model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
String entidad=(String)request.getAttribute("entidad");
if(entidad==null) entidad="evento";
Integer editar=(Integer)request.getAttribute("registroEditar");
List<Usuario> usuarios=(List<Usuario>)request.getAttribute("usuarios");
List<Evento> eventos=(List<Evento>)request.getAttribute("eventos");
List<Categoria> categorias=(List<Categoria>)request.getAttribute("categorias");
List<Lugar> lugares=(List<Lugar>)request.getAttribute("lugares");
String msg=request.getParameter("mensaje");
%>

<%! 
    String esc(String v) {
        return v == null ? "" : v
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;");
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Administración - CulturaTech Bogotá</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,400,0,0" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/estilos.css">
</head>

<body class="admin-page">
    <div class="admin-shell">

        <div class="admin-back">
            <a href="<%=request.getContextPath()%>/pages/home.html">
                <span class="material-symbols-outlined">arrow_back</span>
                Volver al proyecto
            </a>
        </div>

        <main class="admin-sheet">

            <header class="admin-top">
                <div class="admin-brand">
                    <div class="admin-brand-logo">
                        <img src="<%=request.getContextPath()%>/assets/images/logo_CulturaTechBG.png"
                             alt="CulturaTech Bogotá">
                    </div>
                    <div>
                        <span class="admin-eyebrow">GESTIÓN DEL SISTEMA</span>
                        <h1>Panel administrativo</h1>
                        <p>Administra usuarios, eventos, categorías y lugares desde un único espacio.</p>
                    </div>
                </div>
            </header>

            <section class="admin-content">

                <% if (msg != null) {%>
                <div class="admin-alert admin-alert-success">
                    <span class="material-symbols-outlined">check_circle</span>
                    <span><%=esc(msg)%></span>
                </div>
                <% } %>

                <% if (request.getAttribute("error") != null) {%>
                <div class="admin-alert admin-alert-error">
                    <span class="material-symbols-outlined">error</span>
                    <span><%=esc((String) request.getAttribute("error"))%></span>
                </div>
                <% }%>

                <nav class="admin-nav" aria-label="Secciones administrativas">
                    <a href="?entidad=usuario" class="<%=entidad.equals("usuario") ? "active" : ""%>">
                        <span class="material-symbols-outlined">group</span>
                        Usuarios
                    </a>
                    <a href="?entidad=evento" class="<%=entidad.equals("evento") ? "active" : ""%>">
                        <span class="material-symbols-outlined">event</span>
                        Eventos
                    </a>
                    <a href="?entidad=categoria" class="<%=entidad.equals("categoria") ? "active" : ""%>">
                        <span class="material-symbols-outlined">category</span>
                        Categorías
                    </a>
                    <a href="?entidad=lugar" class="<%=entidad.equals("lugar") ? "active" : ""%>">
                        <span class="material-symbols-outlined">location_on</span>
                        Lugares
                    </a>
                </nav>

                <% if (entidad.equals("usuario")) {%>
                <section>
                    <div class="admin-section-head">
                        <div>
                            <h2>Gestión de usuarios</h2>
                            <p>Registra, modifica y elimina cuentas del sistema.</p>
                        </div>
                        <span class="admin-stat"><%=usuarios == null ? 0 : usuarios.size()%> registros</span>
                    </div>

                    <div class="admin-workspace">
                        <div class="admin-editor">
                            <h3><%=editar == null ? "Registrar usuario" : "Actualizar usuario"%></h3>
                            <form method="post" action="<%=request.getContextPath()%>/AdminServlet" class="admin-form-grid">
                                <input type="hidden" name="entidad" value="usuario">
                                <input type="hidden" name="accion" value="<%=editar == null ? "crear" : "actualizar"%>">
                                <% if (editar != null) {%><input type="hidden" name="id" value="<%=editar%>"><% } %>
                                <% Usuario eu = null;
                                    if (editar != null && usuarios != null)
                                        for (Usuario x : usuarios)
                                            if (x.getIdUsuario() == editar)
                                                eu = x;%>

                                <div class="admin-field">
                                    <label for="nombres">Nombres</label>
                                    <input id="nombres" name="nombres" required value="<%=eu == null ? "" : esc(eu.getNombres())%>">
                                </div>
                                <div class="admin-field">
                                    <label for="apellidos">Apellidos</label>
                                    <input id="apellidos" name="apellidos" required value="<%=eu == null ? "" : esc(eu.getApellidos())%>">
                                </div>
                                <div class="admin-field">
                                    <label for="documento">Documento</label>
                                    <input id="documento" name="documento" required value="<%=eu == null ? "" : esc(eu.getDocumento())%>">
                                </div>
                                <div class="admin-field">
                                    <label for="fechaNacimiento">Fecha de nacimiento</label>
                                    <input id="fechaNacimiento" type="date" name="fechaNacimiento" required value="<%=eu == null ? "" : eu.getFechaNacimiento()%>">
                                </div>
                                <div class="admin-field admin-field-full">
                                    <label for="correo">Correo electrónico</label>
                                    <input id="correo" type="email" name="correo" required value="<%=eu == null ? "" : esc(eu.getCorreo())%>">
                                </div>
                                <div class="admin-field">
                                    <label for="contrasena">Contraseña</label>
                                    <input id="contrasena" type="password" name="contrasena" minlength="8" placeholder="<%=editar == null ? "Obligatoria" : "Nueva contraseña (opcional)"%>">
                                </div>
                                <div class="admin-field">
                                    <label for="rol">Rol</label>
                                    <select id="rol" name="rol">
                                        <option value="USER" <%=eu != null && "USER".equals(eu.getRol()) ? "selected" : ""%>>USER</option>
                                        <option value="ADMIN" <%=eu != null && "ADMIN".equals(eu.getRol()) ? "selected" : ""%>>ADMIN</option>
                                    </select>
                                </div>

                                <div class="admin-form-actions">
                                    <% if (editar != null) { %>
                                    <a class="admin-btn admin-btn-secondary" href="?entidad=usuario">
                                        <span class="material-symbols-outlined">close</span>
                                        Cancelar
                                    </a>
                                    <% }%>
                                    <button type="submit" class="admin-btn admin-btn-primary">
                                        <span class="material-symbols-outlined"><%=editar == null ? "person_add" : "save"%></span>
                                        <%=editar == null ? "Registrar usuario" : "Guardar cambios"%>
                                    </button>
                                </div>
                            </form>
                        </div>

                        <div class="admin-list">
                            <div class="admin-list-header">
                                <h3>Usuarios registrados</h3>
                            </div>
                            <div class="admin-table-wrap">
                                <table class="admin-table">
                                    <thead>
                                        <tr><th>ID</th><th>Nombre</th><th>Correo</th><th>Rol</th><th>Acciones</th></tr>
                                    </thead>
                                    <tbody>
                                        <% for (Usuario x : usuarios) {%>
                                        <tr>
                                            <td class="admin-id"><%=x.getIdUsuario()%></td>
                                            <td><%=esc(x.getNombres() + " " + x.getApellidos())%></td>
                                            <td><%=esc(x.getCorreo())%></td>
                                            <td><span class="admin-role"><%=esc(x.getRol())%></span></td>
                                            <td>
                                                <div class="admin-actions">
                                                    <a class="admin-action-link" href="?entidad=usuario&editar=<%=x.getIdUsuario()%>">Editar</a>
                                                    <form class="inline" method="post" action="<%=request.getContextPath()%>/AdminServlet" onsubmit="return confirm('¿Eliminar este usuario?');">
                                                        <input type="hidden" name="entidad" value="usuario">
                                                        <input type="hidden" name="accion" value="eliminar">
                                                        <input type="hidden" name="id" value="<%=x.getIdUsuario()%>">
                                                        <button type="submit" class="admin-action-button">Eliminar</button>
                                                    </form>
                                                </div>
                                            </td>
                                        </tr>
                                        <% } %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </section>

                <% } else if (entidad.equals("evento")) {%>
                <section>
                    <div class="admin-section-head">
                        <div>
                            <h2>Gestión de eventos</h2>
                            <p>Administra la información que se refleja en las interfaces de eventos.</p>
                        </div>
                        <span class="admin-stat"><%=eventos == null ? 0 : eventos.size()%> registros</span>
                    </div>

                    <div class="admin-workspace">
                        <div class="admin-editor">
                            <h3><%=editar == null ? "Registrar evento" : "Actualizar evento"%></h3>
                            <% Evento ee = null;
                                if (editar != null && eventos != null)
                                    for (Evento x : eventos)
                                        if (x.getIdEvento() == editar)
                                            ee = x;%>
                            <form method="post" action="<%=request.getContextPath()%>/AdminServlet" class="admin-form-grid">
                                <input type="hidden" name="entidad" value="evento">
                                <input type="hidden" name="accion" value="<%=editar == null ? "crear" : "actualizar"%>">
                                <% if (editar != null) {%><input type="hidden" name="id" value="<%=editar%>"><% }%>

                                <div class="admin-field admin-field-full"><label for="titulo">Título</label><input id="titulo" name="titulo" required value="<%=ee == null ? "" : esc(ee.getTitulo())%>"></div>
                                <div class="admin-field admin-field-full"><label for="descripcion">Descripción</label><textarea id="descripcion" name="descripcion"><%=ee == null ? "" : esc(ee.getDescripcion())%></textarea></div>
                                <div class="admin-field"><label for="fechaHora">Fecha y hora</label><input id="fechaHora" type="datetime-local" name="fechaHora" required value="<%=ee == null ? "" : ee.getFechaHora().toString().substring(0, 16)%>"></div>
                                <div class="admin-field"><label for="costo">Costo</label><input id="costo" type="number" step="0.01" min="0" name="costo" value="<%=ee == null ? "0" : ee.getCosto()%>"></div>
                                <div class="admin-field admin-field-full"><label for="imagen">URL de imagen</label><input id="imagen" name="imagen" value="<%=ee == null ? "" : esc(ee.getImagen())%>"></div>
                                <div class="admin-field"><label for="estado">Estado</label><select id="estado" name="estado"><option value="ACTIVO" <%=ee == null || "ACTIVO".equals(ee.getEstado()) ? "selected" : ""%>>ACTIVO</option><option value="INACTIVO" <%=ee != null && "INACTIVO".equals(ee.getEstado()) ? "selected" : ""%>>INACTIVO</option></select></div>
                                <div class="admin-field"><label for="idCategoria">Categoría</label><select id="idCategoria" name="idCategoria" required><% for (Categoria x : categorias) {%><option value="<%=x.getIdCategoria()%>" <%=ee != null && x.getIdCategoria() == ee.getIdCategoria() ? "selected" : ""%>><%=esc(x.getNombreCategoria())%></option><% } %></select></div>
                                <div class="admin-field"><label for="idLugar">Lugar</label><select id="idLugar" name="idLugar" required><% for (Lugar x : lugares) {%><option value="<%=x.getIdLugar()%>" <%=ee != null && x.getIdLugar() == ee.getIdLugar() ? "selected" : ""%>><%=esc(x.getNombreLugar())%></option><% } %></select></div>

                                <div class="admin-form-actions">
                                    <% if (editar != null) { %>
                                    <a class="admin-btn admin-btn-secondary" href="?entidad=evento"><span class="material-symbols-outlined">close</span>Cancelar</a>
                                    <% }%>
                                    <button type="submit" class="admin-btn admin-btn-primary"><span class="material-symbols-outlined"><%=editar == null ? "add" : "save"%></span><%=editar == null ? "Registrar evento" : "Guardar cambios"%></button>
                                </div>
                            </form>
                        </div>

                        <div class="admin-list">
                            <div class="admin-list-header"><h3>Eventos registrados</h3></div>
                            <div class="admin-card-grid">
                                <% for (Evento x : eventos) {%>
                                <article class="admin-event-card">
                                    <h4><%=esc(x.getTitulo())%></h4>
                                    <p class="admin-event-meta"><%=esc(x.getNombreCategoria())%> · <%=esc(x.getNombreLugar())%></p>
                                    <p class="admin-event-description"><%=esc(x.getDescripcion())%></p>
                                    <div class="admin-card-actions">
                                        <a class="admin-action-link" href="?entidad=evento&editar=<%=x.getIdEvento()%>">Editar</a>
                                        <form class="inline" method="post" action="<%=request.getContextPath()%>/AdminServlet" onsubmit="return confirm('¿Eliminar este evento?');">
                                            <input type="hidden" name="entidad" value="evento"><input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="<%=x.getIdEvento()%>">
                                            <button type="submit" class="admin-action-button">Eliminar</button>
                                        </form>
                                    </div>
                                </article>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </section>

                <% } else if (entidad.equals("categoria")) {%>
                <section>
                    <div class="admin-section-head">
                        <div><h2>Gestión de categorías</h2><p>Administra las categorías utilizadas para clasificar eventos.</p></div>
                        <span class="admin-stat"><%=categorias == null ? 0 : categorias.size()%> registros</span>
                    </div>
                    <div class="admin-workspace">
                        <div class="admin-editor">
                            <h3><%=editar == null ? "Registrar categoría" : "Actualizar categoría"%></h3>
                            <% Categoria ec = null;
                                if (editar != null && categorias != null)
                                    for (Categoria x : categorias)
                                        if (x.getIdCategoria() == editar)
                                            ec = x;%>
                            <form method="post" action="<%=request.getContextPath()%>/AdminServlet" class="admin-form-grid">
                                <input type="hidden" name="entidad" value="categoria"><input type="hidden" name="accion" value="<%=editar == null ? "crear" : "actualizar"%>"><%if (editar != null) {%><input type="hidden" name="id" value="<%=editar%>"><%}%>
                                <div class="admin-field admin-field-full"><label for="nombreCategoria">Nombre</label><input id="nombreCategoria" name="nombreCategoria" required value="<%=ec == null ? "" : esc(ec.getNombreCategoria())%>"></div>
                                <div class="admin-field admin-field-full"><label for="descripcionCategoria">Descripción</label><textarea id="descripcionCategoria" name="descripcion"><%=ec == null ? "" : esc(ec.getDescripcion())%></textarea></div>
                                <div class="admin-form-actions"><%if (editar != null) {%><a class="admin-btn admin-btn-secondary" href="?entidad=categoria"><span class="material-symbols-outlined">close</span>Cancelar</a><%}%><button class="admin-btn admin-btn-primary" type="submit"><span class="material-symbols-outlined"><%=editar == null ? "add" : "save"%></span><%=editar == null ? "Registrar categoría" : "Guardar cambios"%></button></div>
                            </form>
                        </div>
                        <div class="admin-list">
                            <div class="admin-list-header"><h3>Categorías registradas</h3></div>
                            <div class="admin-simple-list">
                                <%for (Categoria x : categorias) {%>
                                <div class="admin-simple-row">
                                    <div class="admin-simple-info"><strong><%=esc(x.getNombreCategoria())%></strong><p><%=esc(x.getDescripcion())%></p></div>
                                    <div class="admin-actions"><a class="admin-action-link" href="?entidad=categoria&editar=<%=x.getIdCategoria()%>">Editar</a><form class="inline" method="post" action="<%=request.getContextPath()%>/AdminServlet" onsubmit="return confirm('¿Eliminar esta categoría?');"><input type="hidden" name="entidad" value="categoria"><input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="<%=x.getIdCategoria()%>"><button type="submit" class="admin-action-button">Eliminar</button></form></div>
                                </div>
                                <%}%>
                            </div>
                        </div>
                    </div>
                </section>

                <% } else {%>
                <section>
                    <div class="admin-section-head">
                        <div><h2>Gestión de lugares</h2><p>Administra los lugares asociados a los eventos culturales.</p></div>
                        <span class="admin-stat"><%=lugares == null ? 0 : lugares.size()%> registros</span>
                    </div>
                    <div class="admin-workspace">
                        <div class="admin-editor">
                            <h3><%=editar == null ? "Registrar lugar" : "Actualizar lugar"%></h3>
                            <% Lugar el = null;
                                if (editar != null && lugares != null)
                                    for (Lugar x : lugares)
                                        if (x.getIdLugar() == editar)
                                            el = x;%>
                            <form method="post" action="<%=request.getContextPath()%>/AdminServlet" class="admin-form-grid">
                                <input type="hidden" name="entidad" value="lugar"><input type="hidden" name="accion" value="<%=editar == null ? "crear" : "actualizar"%>"><%if (editar != null) {%><input type="hidden" name="id" value="<%=editar%>"><%}%>
                                <div class="admin-field"><label for="nombreLugar">Nombre</label><input id="nombreLugar" name="nombreLugar" required value="<%=el == null ? "" : esc(el.getNombreLugar())%>"></div>
                                <div class="admin-field"><label for="localidad">Localidad</label><input id="localidad" name="localidad" required value="<%=el == null ? "" : esc(el.getLocalidad())%>"></div>
                                <div class="admin-field admin-field-full"><label for="direccion">Dirección</label><input id="direccion" name="direccion" required value="<%=el == null ? "" : esc(el.getDireccion())%>"></div>
                                <div class="admin-field"><label for="latitud">Latitud</label><input id="latitud" type="number" step="0.00000001" name="latitud" value="<%=el == null || el.getLatitud() == null ? "" : el.getLatitud()%>"></div>
                                <div class="admin-field"><label for="longitud">Longitud</label><input id="longitud" type="number" step="0.00000001" name="longitud" value="<%=el == null || el.getLongitud() == null ? "" : el.getLongitud()%>"></div>
                                <div class="admin-field"><label for="telefono">Teléfono</label><input id="telefono" name="telefono" value="<%=el == null ? "" : esc(el.getTelefono())%>"></div>
                                <div class="admin-field"><label for="paginaWeb">Página web</label><input id="paginaWeb" type="url" name="paginaWeb" value="<%=el == null ? "" : esc(el.getPaginaWeb())%>"></div>
                                <div class="admin-form-actions"><%if (editar != null) {%><a class="admin-btn admin-btn-secondary" href="?entidad=lugar"><span class="material-symbols-outlined">close</span>Cancelar</a><%}%><button class="admin-btn admin-btn-primary" type="submit"><span class="material-symbols-outlined"><%=editar == null ? "add" : "save"%></span><%=editar == null ? "Registrar lugar" : "Guardar cambios"%></button></div>
                            </form>
                        </div>
                        <div class="admin-list">
                            <div class="admin-list-header"><h3>Lugares registrados</h3></div>
                            <div class="admin-table-wrap">
                                <table class="admin-table">
                                    <thead><tr><th>ID</th><th>Lugar</th><th>Dirección</th><th>Localidad</th><th>Acciones</th></tr></thead>
                                    <tbody>
                                        <%for (Lugar x : lugares) {%>
                                        <tr><td class="admin-id"><%=x.getIdLugar()%></td><td><%=esc(x.getNombreLugar())%></td><td><%=esc(x.getDireccion())%></td><td><%=esc(x.getLocalidad())%></td><td><div class="admin-actions"><a class="admin-action-link" href="?entidad=lugar&editar=<%=x.getIdLugar()%>">Editar</a><form class="inline" method="post" action="<%=request.getContextPath()%>/AdminServlet" onsubmit="return confirm('¿Eliminar este lugar?');"><input type="hidden" name="entidad" value="lugar"><input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="<%=x.getIdLugar()%>"><button type="submit" class="admin-action-button">Eliminar</button></form></div></td></tr>
                                                <%}%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </section>
                <% }%>

            </section>
        </main>

        <div class="admin-footer-note">
            CulturaTech Bogotá · Gestión administrativa del sistema. Carlos Rodrigo Ortegón · ADSO-3235898.
        </div>
    </div>

    <script>
        document.documentElement.classList.add('admin-ready');
    </script>
</body>
</html>