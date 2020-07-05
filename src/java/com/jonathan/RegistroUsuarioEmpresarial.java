package com.jonathan;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class RegistroUsuarioEmpresarial extends HttpServlet {
    @Resource(name = "jdbc/ProyectoEstructuras")
    private DataSource miPool;

    public RegistroUsuarioEmpresarial() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

        String id_usuario = request.getParameter("id_usuario");
        String correo = request.getParameter("correo");

        try {
            Connection conexion = miPool.getConnection();

            String miQuery = "INSERT INTO usuario_empresarial (id_usuario, correo) VALUES (?, ?)";

            PreparedStatement preparedStmt = conexion.prepareStatement(miQuery);

            preparedStmt.setString(1, id_usuario);
            preparedStmt.setString(2, correo);
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
