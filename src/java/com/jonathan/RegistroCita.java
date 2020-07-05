package com.jonathan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class RegistroCita extends HttpServlet {
    @Resource(name = "jdbc/ProyectoEstructuras")
    private DataSource miPool;

    public RegistroCita() {
        super();
   }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	String id_usuario = request.getParameter("id");

	Calendar calendar = Calendar.getInstance();
	Date ourJavaDateObject = new Date(calendar.getTime().getTime());

	DateTimeFormatter hh = DateTimeFormatter.ofPattern("HH");
	LocalDateTime now = LocalDateTime.now();
	DateTimeFormatter mm = DateTimeFormatter.ofPattern("mm");
	int hora = Integer.parseInt(hh.format(now));
	int minuto = Integer.parseInt(mm.format(now));

	try {
            Connection conexion = miPool.getConnection();

            String miQuery = "INSERT INTO cita (fecha, hora, minuto, atendido, id_usuario) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = conexion.prepareStatement(miQuery);

            preparedStmt.setDate(1, ourJavaDateObject);
            preparedStmt.setInt(2, hora);
            preparedStmt.setInt(3, minuto);
            preparedStmt.setInt(4, 1);
            preparedStmt.setString(5, id_usuario);
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
