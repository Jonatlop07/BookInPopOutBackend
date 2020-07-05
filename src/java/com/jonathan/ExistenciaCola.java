package com.jonathan;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExistenciaCola extends HttpServlet {
    public ExistenciaCola() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        PrintWriter respuesta = response.getWriter();

        if (ColaPrioridad.cola == null) {
            respuesta.print("No existe cola");
	} else {
            String s = "";
            for (int i = 0; i < ColaPrioridad.cola.size(); i++) {
                s += ColaPrioridad.cola.heap[i] + "\n";
            }
            
            respuesta.print(s);
	}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	doGet(request, response);
    }

}
