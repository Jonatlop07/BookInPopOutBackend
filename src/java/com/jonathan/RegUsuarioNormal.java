package com.jonathan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class RegUsuarioNormal extends HttpServlet {
    @Resource(name = "jdbc/ProyectoEstructuras")
    private DataSource miPool;

    public RegUsuarioNormal() {
	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
            
        String id_usuario = request.getParameter("id_usuario");
        String id_documento = request.getParameter("id_documento");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String fecha_nacimiento = request.getParameter("fecha_nacimiento");
        boolean discapacitado = Boolean.parseBoolean(request.getParameter("discapacitado"));
        String correo = request.getParameter("correo");

	try {
            Connection conexion = miPool.getConnection();

            String miQuery = "INSERT INTO usuario (id_usuario, id_ubicacion ," 
                            + " id_documento, nombre, apellido, fecha_nacimiento," 
                            + " correo, discapacitado) VALUES (?, 1 , ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = conexion.prepareStatement(miQuery);

            preparedStmt.setString(1, id_usuario);
            preparedStmt.setString(2, id_documento);
            preparedStmt.setString(3, nombre);
            preparedStmt.setString(4, apellido);
            preparedStmt.setString(5, fecha_nacimiento);
            preparedStmt.setString(6, correo);
            preparedStmt.setBoolean(7, discapacitado);
            preparedStmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
	}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	doGet(request, response);
    }
}
