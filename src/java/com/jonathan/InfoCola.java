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


public class InfoCola extends HttpServlet {

    @Resource(name = "jdbc/ProyectoEstructuras")
    private DataSource miPool;

    public InfoCola() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            PrintWriter respuesta = response.getWriter();
            
            String articulo = "[";
            
            if (ColaPrioridad.cola == null || ColaPrioridad.cola.isEmpty()) {
                respuesta.print(articulo + "]");
            } else {
                User[] users = ColaPrioridad.cola.heap;

                Connection conexion = miPool.getConnection();

                for (int i = 0; i < ColaPrioridad.cola.size() && users[i] != null; i++) {

                    String miquery = "SELECT nombre, apellido, id_documento FROM usuario WHERE id_usuario = '"
                            + users[i].getUid() + "'";
                    Statement sentencia = conexion.createStatement();
                    ResultSet salida = sentencia.executeQuery(miquery);

                    if (salida.isBeforeFirst()) {
                        salida.next();
                        articulo += "{documento : \"" + salida.getString(3) + "\", nombre : \"" + salida.getString(1) + " "
                                + salida.getString(2) + "\", hora : \"" + users[i].getAttentionHour() + "\"}";
                        articulo += ",";
                    }
                }

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
