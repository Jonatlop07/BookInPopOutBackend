package com.jonathan;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class InicioSesion extends HttpServlet {

    @Resource(name = "jdbc/ProyectoEstructuras")
    private DataSource miPool;

    public InicioSesion() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String email = request.getParameter("email");

        try {
            Connection conexion = miPool.getConnection();
            String miQuery = "SELECT * FROM usuario_empresarial WHERE id_usuario = '" + id + "' AND correo = '" + email
                    + "'";
            Statement sentencia = conexion.createStatement();
            ResultSet salida = sentencia.executeQuery(miQuery);

            String articulo = "";

            if (salida.isBeforeFirst()) {
                articulo = "{empleado : \"true\"}";
            } else {
                articulo = "{empleado : \"false\"}";
            }
            
            PrintWriter respuesta = response.getWriter();
            respuesta.println(articulo);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
