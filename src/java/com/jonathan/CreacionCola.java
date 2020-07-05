package com.jonathan;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreacionCola extends HttpServlet {

    public CreacionCola() {
	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        
	int tamCola = Integer.parseInt(request.getParameter("tamanioCola"));
	String horaInicial = request.getParameter("horaInicial");
	String horaFinal = request.getParameter("horaFinal");
	int minIntervalo = Integer.parseInt(request.getParameter("minutosIntervalo"));

	ServiceMetaData metaData = new ServiceMetaData();
	metaData.setMaxSize(tamCola);
	metaData.setStartHour( LocalTime.of(Integer.parseInt(horaInicial.split(":")[0]),
                                Integer.parseInt(horaInicial.split(":")[1])));
        
	metaData.setEndHour( LocalTime.of(Integer.parseInt(horaFinal.split(":")[0]), 
                                Integer.parseInt(horaFinal.split(":")[1])));
        
	metaData.setIntervalLength(minIntervalo);

	ColaPrioridad.setUpPriorityQueue(metaData);

	PrintWriter respuesta = response.getWriter();
	respuesta.print(ColaPrioridad.cola);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	doGet(request, response);
    }
}
