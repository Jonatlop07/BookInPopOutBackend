package com.jonathan;

import java.io.IOException;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CancelarCita extends HttpServlet {

    public CancelarCita() {
	super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        String id_usuario = request.getParameter("id");
        int hora = Integer.parseInt(request.getParameter("hora"));
        int minuto = Integer.parseInt(request.getParameter("minuto"));

	try {
            if (!ColaPrioridad.bufferQueue.deleteUser(id_usuario, LocalTime.of(hora, minuto))) {
                ColaPrioridad.cola.remove(id_usuario);
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
