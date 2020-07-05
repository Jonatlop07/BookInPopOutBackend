package com.jonathan;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import javax.sql.DataSource;

public class Desencolamiento extends HttpServlet {
    @Resource(name = "jdbc/ProyectoEstructuras")
    private DataSource miPool;

    public Desencolamiento() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (!ColaPrioridad.cola.isEmpty()){
                Connection conexion = miPool.getConnection();

                Calendar calendar = Calendar.getInstance();
                Date date = new Date(calendar.getTime().getTime());

                User userExtracted = ColaPrioridad.cola.extractMax();
                String query = "INSERT INTO cita(id_usuario, hora, minuto, fecha, atendido) VALUES (?, ?, ?, ?, ?)";
            
                PreparedStatement preparedStatement = conexion.prepareStatement(query);
                preparedStatement.setString(1, userExtracted.getUid());
                preparedStatement.setInt(2, userExtracted.getAttentionHour().getHour());
                preparedStatement.setInt(3, userExtracted.getAttentionHour().getMinute());
                preparedStatement.setDate(4, date);
                preparedStatement.setInt(5, 1);
                preparedStatement.execute();
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
