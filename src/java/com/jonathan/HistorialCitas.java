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

public class HistorialCitas extends HttpServlet {

    @Resource(name = "jdbc/ProyectoEstructuras")
    private DataSource miPool;

    public HistorialCitas() {
	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	String id = request.getParameter("id");
	String fecha = request.getParameter("fecha");

	try {
            Connection conexion = miPool.getConnection();
            String miquery = "SELECT hora, minuto, atendido FROM cita WHERE id_usuario = '" + id + "' AND fecha = '"
					+ fecha + "'";
            Statement sentencia = conexion.createStatement();
            ResultSet salida = sentencia.executeQuery(miquery);
            String articulo = "[";

            while (salida.next()) {
		articulo += "{hora : \"" + salida.getString(1) + ":" + salida.getString(2) + "\", atendido : \""
			    + salida.getBoolean(3) + "\"},";
            }
            
            PrintWriter respuesta = response.getWriter();
            
            if (articulo.contentEquals("[")) {
		respuesta.print(articulo.substring(0, articulo.length()) + "]");
            } else {
		respuesta.print(articulo.substring(0, articulo.length() - 1) + "]");
            }
            
	} catch (Exception e) {
            e.printStackTrace();
	}
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	doGet(request, response);
    }
}
