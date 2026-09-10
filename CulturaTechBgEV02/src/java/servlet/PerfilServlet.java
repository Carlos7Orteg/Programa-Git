package servlet;

import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import model.Usuario;
import util.ConexionBD;

@WebServlet(name="PerfilServlet", urlPatterns={"/PerfilServlet"})
public class PerfilServlet extends HttpServlet {
    private static final int ITERACIONES=120000, LONGITUD_SAL=16, LONGITUD_HASH=256;

    private boolean autenticado(HttpServletRequest r){
        HttpSession s=r.getSession(false);
        return s!=null && Boolean.TRUE.equals(s.getAttribute("usuarioAutenticado"));
    }

    @Override protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException {
        if(!autenticado(request)){ response.sendRedirect(request.getContextPath()+"/pages/login.html"); return; }
        HttpSession s=request.getSession(false);
        int id=(Integer)s.getAttribute("usuarioId");
        try(Connection c=ConexionBD.conectar()){
            Usuario u=new UsuarioDAO().buscarPorId(c,id);
            if(u==null){ s.invalidate(); response.sendRedirect(request.getContextPath()+"/pages/login.html"); return; }
            request.setAttribute("usuario",u);
            request.getRequestDispatcher("/WEB-INF/views/perfil.jsp").forward(request,response);
        }catch(Exception e){ throw new ServletException("No se pudo cargar el perfil.",e); }
    }

    @Override protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException {
        request.setCharacterEncoding("UTF-8");
        if(!autenticado(request)){ response.sendRedirect(request.getContextPath()+"/pages/login.html"); return; }
        HttpSession s=request.getSession(false);
        int id=(Integer)s.getAttribute("usuarioId");
        String accion=request.getParameter("accion");
        try(Connection c=ConexionBD.conectar()){
            UsuarioDAO dao=new UsuarioDAO();
            if("eliminar".equals(accion)){
                dao.eliminar(c,id);
                s.invalidate();
                response.sendRedirect(request.getContextPath()+"/pages/login.html?cuenta=eliminada");
                return;
            }
            Usuario u=new Usuario();
            u.setIdUsuario(id);
            u.setNombres(required(request,"nombres"));
            u.setApellidos(required(request,"apellidos"));
            u.setDocumento(required(request,"documento"));
            u.setFechaNacimiento(LocalDate.parse(required(request,"fechaNacimiento")));
            u.setCorreo(required(request,"correo"));
            String nueva=request.getParameter("contrasena");
            boolean cambiar=nueva!=null && !nueva.isBlank();
            if(cambiar) u.setContrasena(hashPassword(nueva));
            dao.actualizarPerfil(c,u,cambiar);
            s.setAttribute("usuarioCorreo",u.getCorreo());
            request.setAttribute("mensaje","Perfil actualizado correctamente.");
            Usuario actualizado=dao.buscarPorId(c,id);
            request.setAttribute("usuario",actualizado);
            request.getRequestDispatcher("/WEB-INF/views/perfil.jsp").forward(request,response);
        }catch(Exception e){
            request.setAttribute("error","No fue posible actualizar el perfil. Verifica los datos.");
            doGetConError(request,response,e);
        }
    }
    private void doGetConError(HttpServletRequest req,HttpServletResponse res,Exception original)
            throws ServletException,IOException{
        try(Connection c=ConexionBD.conectar()){
            int id=(Integer)req.getSession(false).getAttribute("usuarioId");
            req.setAttribute("usuario",new UsuarioDAO().buscarPorId(c,id));
            req.getRequestDispatcher("/WEB-INF/views/perfil.jsp").forward(req,res);
        }catch(Exception e){ throw new ServletException("Error al mostrar el perfil.",original); }
    }
    private String required(HttpServletRequest r,String p){
        String v=r.getParameter(p);
        if(v==null||v.isBlank()) throw new IllegalArgumentException("Campo obligatorio: "+p);
        return v.trim();
    }
    private String hashPassword(String password)throws Exception{
        byte[] salt=new byte[LONGITUD_SAL]; new SecureRandom().nextBytes(salt);
        PBEKeySpec spec=new PBEKeySpec(password.toCharArray(),salt,ITERACIONES,LONGITUD_HASH);
        try{
            byte[] hash=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
            return ITERACIONES+":"+Base64.getEncoder().encodeToString(salt)+":"+Base64.getEncoder().encodeToString(hash);
        }finally{spec.clearPassword();}
    }
}
