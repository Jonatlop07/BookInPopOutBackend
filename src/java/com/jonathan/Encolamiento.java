package com.jonathan;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class Encolamiento extends HttpServlet {

    @Resource(name = "jdbc/ProyectoEstructuras")
    private DataSource miPool;

    public Encolamiento() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id_usuario = request.getParameter("id");
        int hora = Integer.parseInt(request.getParameter("hora"));
        int minuto = Integer.parseInt(request.getParameter("minuto"));
        LocalTime horaCita = LocalTime.of(hora, minuto);
        double prioridad = 0;

        try {
            Connection conexion = miPool.getConnection();
            String consultarDatosPrioridad = "SELECT fecha_nacimiento, discapacitado FROM usuario WHERE id_usuario = '"
                                             + id_usuario + "'";
            Statement sentencia = conexion.createStatement();
            ResultSet salida = sentencia.executeQuery(consultarDatosPrioridad);
            

            if (salida.isBeforeFirst()) {
                salida.next();
                LocalDate fechaNacimiento = Instant.ofEpochMilli(salida.getDate(1).getTime())
                        .atZone(ZoneId.systemDefault()).toLocalDate();

                if (fechaNacimiento.compareTo(LocalDate.of(2005, 1, 1)) < 0) {
                    prioridad++;
                } else if (fechaNacimiento.compareTo(LocalDate.of(1961, 1, 1)) < 0) {
                    prioridad++;
                }

                if (salida.getBoolean(2)) {
                    prioridad++;
                }
            }

            String consultarImagenCliente = "SELECT COUNT(*) FROM cita WHERE id_usuario = '" + id_usuario
                    + "' AND atendido = False";
            Statement sentencia2 = conexion.createStatement();
            salida = sentencia2.executeQuery(consultarImagenCliente);
            
            if (salida.next()){
                prioridad -= salida.getInt(1);
            }
            
            boolean encolado = ColaPrioridad.bufferQueue.insertUser(new User(horaCita, id_usuario, prioridad));
            
            PrintWriter respuesta = response.getWriter();
            
            if (encolado) {
                respuesta.println("{encolado: \"true\"}");
            } else {
                respuesta.println("{encolado: \"false\"}");
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
