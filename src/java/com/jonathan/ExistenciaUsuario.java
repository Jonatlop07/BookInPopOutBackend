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

public class ExistenciaUsuario extends HttpServlet {
    @Resource(name = "jdbc/ProyectoEstructuras")
    private DataSource miPool;

    public ExistenciaUsuario() {
	super();
    }
        
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        String doc = request.getParameter("documento");

	try {
            Connection conexion = miPool.getConnection();

            String miquery = "SELECT id_usuario FROM usuario WHERE id_documento = '" + doc + "'";
            Statement sentencia = conexion.createStatement();
            ResultSet salida = sentencia.executeQuery(miquery);
            
            String articulo = "";
            
            if (salida.isBeforeFirst()) {
                salida.next();
		articulo = "{existe : \"true\", id:\"" + salida.getString(1) + "\"}";
            } else {
                articulo = "{existe : \"false\"}";
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
