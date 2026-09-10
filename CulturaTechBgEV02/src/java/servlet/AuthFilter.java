/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/pages/*"})
/**
 *
 * @author Carlos
 */
public class AuthFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        String pagina =
                httpRequest.getRequestURI()
                        .substring(
                                httpRequest.getRequestURI()
                                        .lastIndexOf("/") + 1
                        )
                        .toLowerCase();

        // Páginas que pueden abrirse sin autenticación.
        boolean paginaPublica =
                pagina.equals("login.html")
                || pagina.equals("register.html");
            
        if (paginaPublica) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession sesion =
                httpRequest.getSession(false);

        boolean autenticado =
                sesion != null
                && Boolean.TRUE.equals(
                        sesion.getAttribute("usuarioAutenticado")
                );

        if (!autenticado) {

            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                    + "/pages/login.html"
            );

            return;
        }

        chain.doFilter(request, response);
    }
}