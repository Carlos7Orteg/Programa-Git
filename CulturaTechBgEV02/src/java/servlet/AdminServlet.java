package servlet;
// Autoría: Carlos Rodrigo Ortegón | ADSO-3235898 CulturaTech Bogotá | 2026
import dao.AdminDAO;
import dao.EventoDAO;
import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import model.*;

@WebServlet(name="AdminServlet", urlPatterns={"/AdminServlet"})
public class AdminServlet extends HttpServlet {
    private static final int ITERACIONES=120000, SAL=16, HASH=256;

    private boolean esAdmin(HttpServletRequest r){
        HttpSession s=r.getSession(false);
        return s!=null && Boolean.TRUE.equals(s.getAttribute("usuarioAutenticado"))
                && "ADMIN".equals(s.getAttribute("usuarioRol"));
    }
    private void acceso(HttpServletRequest r,HttpServletResponse p)throws IOException{
        p.sendError(HttpServletResponse.SC_FORBIDDEN,"Acceso restringido a administradores.");
    }

    @Override protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
        if(!esAdmin(req)){ acceso(req,res); return; }
        String entidad=valor(req.getParameter("entidad"),"eventos");
        String editar=req.getParameter("editar");
        try(Connection c=util.ConexionBD.conectar()){
            cargar(req,c,entidad,editar);
            req.setAttribute("entidad",entidad);
            req.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(req,res);
        }catch(Exception e){ throw new ServletException("No se pudo cargar el panel administrativo.",e); }
    }

    @Override protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8");
        if(!esAdmin(req)){ acceso(req,res); return; }
        String entidad=valor(req.getParameter("entidad"),"eventos");
        String accion=valor(req.getParameter("accion"),"");
        try(Connection c=util.ConexionBD.conectar()){
            AdminDAO ad=new AdminDAO(); UsuarioDAO ud=new UsuarioDAO(); EventoDAO ed=new EventoDAO();
            if("eliminar".equals(accion)){
                int id=Integer.parseInt(required(req,"id"));
                if("usuario".equals(entidad)) {
                    HttpSession sesion=req.getSession(false);
                    if(sesion!=null && id==((Integer)sesion.getAttribute("usuarioId"))) {
                        throw new IllegalArgumentException("El administrador actual no puede eliminar su propia cuenta desde este panel.");
                    }
                    ud.eliminar(c,id);
                }
                else if("evento".equals(entidad)) ed.eliminar(c,id);
                else if("categoria".equals(entidad)) ad.eliminarCategoria(c,id);
                else if("lugar".equals(entidad)) ad.eliminarLugar(c,id);
                else throw new IllegalArgumentException("Entidad no válida.");
                res.sendRedirect(req.getContextPath()+"/AdminServlet?entidad="+entidad+"&mensaje=Registro+eliminado+correctamente");
                return;
            }
            if("usuario".equals(entidad)){
                Usuario u=new Usuario();
                if(!"crear".equals(accion)) u.setIdUsuario(Integer.parseInt(req.getParameter("id")));
                u.setNombres(required(req,"nombres")); u.setApellidos(required(req,"apellidos"));
                u.setDocumento(required(req,"documento")); u.setFechaNacimiento(LocalDate.parse(required(req,"fechaNacimiento")));
                u.setCorreo(required(req,"correo")); u.setRol("ADMIN".equals(req.getParameter("rol"))?"ADMIN":"USER");
                HttpSession sesionAdmin=req.getSession(false);
                if(!"crear".equals(accion) && sesionAdmin!=null
                        && u.getIdUsuario()==((Integer)sesionAdmin.getAttribute("usuarioId"))
                        && !"ADMIN".equals(u.getRol())) {
                    throw new IllegalArgumentException("El administrador actual no puede quitarse sus propios permisos.");
                }
                String pass=req.getParameter("contrasena");
                if("crear".equals(accion)){
                    if(pass==null||pass.isBlank()) throw new IllegalArgumentException("La contraseña es obligatoria.");
                    u.setContrasena(hash(pass)); ud.insertar(c,u);
                } else {
                    boolean cambiar=pass!=null&&!pass.isBlank();
                    if(cambiar) u.setContrasena(hash(pass));
                    ud.actualizar(c,u,cambiar);
                }
            } else if("evento".equals(entidad)){
                Evento e=new Evento();
                e.setTitulo(required(req,"titulo")); e.setDescripcion(req.getParameter("descripcion"));
                e.setFechaHora(LocalDateTime.parse(required(req,"fechaHora"))); e.setCosto(Double.parseDouble(valor(req.getParameter("costo"),"0")));
                e.setImagen(req.getParameter("imagen")); e.setEstado("INACTIVO".equals(req.getParameter("estado"))?"INACTIVO":"ACTIVO");
                e.setIdCategoria(Integer.parseInt(required(req,"idCategoria"))); e.setIdLugar(Integer.parseInt(required(req,"idLugar")));
                if("crear".equals(accion)) ed.insertar(c,e); else { e.setIdEvento(Integer.parseInt(req.getParameter("id"))); ed.actualizar(c,e); }
            } else if("categoria".equals(entidad)){
                Categoria x=new Categoria(); x.setNombreCategoria(required(req,"nombreCategoria")); x.setDescripcion(req.getParameter("descripcion"));
                if("crear".equals(accion)) ad.insertarCategoria(c,x); else { x.setIdCategoria(Integer.parseInt(req.getParameter("id"))); ad.actualizarCategoria(c,x); }
            } else if("lugar".equals(entidad)){
                Lugar x=new Lugar(); x.setNombreLugar(required(req,"nombreLugar")); x.setDireccion(required(req,"direccion")); x.setLocalidad(required(req,"localidad"));
                x.setLatitud(decimal(req.getParameter("latitud"))); x.setLongitud(decimal(req.getParameter("longitud")));
                x.setTelefono(req.getParameter("telefono")); x.setPaginaWeb(req.getParameter("paginaWeb"));
                if("crear".equals(accion)) ad.insertarLugar(c,x); else { x.setIdLugar(Integer.parseInt(req.getParameter("id"))); ad.actualizarLugar(c,x); }
            }
            else { throw new IllegalArgumentException("Entidad no válida."); }
            res.sendRedirect(req.getContextPath()+"/AdminServlet?entidad="+entidad+"&mensaje=Operacion+realizada+correctamente");
        }catch(Exception e){
            req.setAttribute("error","No fue posible completar la operación: "+e.getMessage());
            doGet(req,res);
        }
    }

    private void cargar(HttpServletRequest req,Connection c,String entidad,String editar)throws SQLException{
        UsuarioDAO ud=new UsuarioDAO(); EventoDAO ed=new EventoDAO(); AdminDAO ad=new AdminDAO();
        req.setAttribute("usuarios",ud.listarTodos(c)); req.setAttribute("eventos",ed.consultarTodas(c));
        req.setAttribute("categorias",ad.listarCategorias(c)); req.setAttribute("lugares",ad.listarLugares(c));
        if(editar!=null&&!editar.isBlank()) req.setAttribute("registroEditar",Integer.valueOf(editar));
    }
    private String valor(String v,String d){return v==null||v.isBlank()?d:v;}
    private String required(HttpServletRequest r,String p){String v=r.getParameter(p);if(v==null||v.isBlank())throw new IllegalArgumentException("Campo obligatorio: "+p);return v.trim();}
    private Double decimal(String v){return v==null||v.isBlank()?null:Double.valueOf(v);}
    private String hash(String password)throws Exception{
        byte[] salt=new byte[SAL];new SecureRandom().nextBytes(salt);
        PBEKeySpec spec=new PBEKeySpec(password.toCharArray(),salt,ITERACIONES,HASH);
        try{byte[] h=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
            return ITERACIONES+":"+Base64.getEncoder().encodeToString(salt)+":"+Base64.getEncoder().encodeToString(h);}
        finally{spec.clearPassword();}
    }
}
