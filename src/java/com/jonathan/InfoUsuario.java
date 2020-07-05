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

public class InfoUsuario extends HttpServlet {

    @Resource(name = "jdbc/ProyectoEstructuras")
    private DataSource miPool;

    public InfoUsuario() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        try {
            Connection conexion = miPool.getConnection();
            String consultarCantidad = "SELECT count(*) FROM usuario";
            Statement sentencia = conexion.createStatement();
            ResultSet salida = sentencia.executeQuery(consultarCantidad);
            
            PrintWriter respuesta = response.getWriter();
            
            if (salida.next()) {
                Lista[] tablaHash = new Lista[salida.getInt(1)];

                String consultarUsuarios = "SELECT id_usuario, nombre, apellido,"
                        + " id_documento, fecha_nacimiento, correo, discapacitado FROM usuario";
                Statement sentencia2 = conexion.createStatement();
                ResultSet salida2 = sentencia2.executeQuery(consultarUsuarios);

                while (salida2.next()) {
                    int pos = hashFunction(salida2.getString(1), tablaHash.length);

                    UsuarioConsulta usuario = new UsuarioConsulta();

                    usuario.setId(salida2.getString(1));
                    usuario.setNombre(salida2.getString(2) + " " + salida2.getString(3));
                    usuario.setDocumento(salida2.getString(4));
                    usuario.setFechaNacimiento(salida2.getString(5));
                    usuario.setCorreo(salida2.getString(6));
                    usuario.setDiscapacitado(salida2.getInt(7) != 0 ? "Sí" : "No");

                    if (tablaHash[pos] == null) {
                        tablaHash[pos] = new Lista();
                    }

                    tablaHash[pos].pushBack(usuario);
                }

                int pos = -1;

                pos = hashFunction(id, tablaHash.length);

                UsuarioConsulta usuario = tablaHash[pos].find(id);

                String usuarioInfo = "{nombre : \"" + usuario.getNombre() + "\", documento : \""
                        + usuario.getDocumento() + "\", fecha_nacimiento : \"" + usuario.getFechaNacimiento()
                        + "\", discapacitado : \"" + usuario.esDiscapacitado().equals("Sí") + "\"}";
                
                respuesta.println(usuarioInfo);
            } else {
                respuesta.println("No se encontró al usuario");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int hashFunction(String id, int n) {
        int x = 63;

        int hash = 0;

        for (int i = id.length() - 1; i > 0; i--) {
            hash = (hash * x + id.charAt(i)) % n;
        }

        return hash;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

class Lista {
    private class Nodo {
        UsuarioConsulta usuario;
        Nodo next = null;

        public Nodo(UsuarioConsulta usuario) {
            this.usuario = usuario;
        }
    }

    private Nodo head = null;

    public Lista() {

    }

    public boolean isEmpty() {
        return head == null;
    }

    public void pushBack(UsuarioConsulta usuario) {
        Nodo nuevoNodo = new Nodo(usuario);

        if (head == null) {
            head = nuevoNodo;
        } else {
            Nodo curr = head, prev = head;

            while (curr != null) {
                prev = curr;
                curr = curr.next;
            }

            prev.next = nuevoNodo;
        }
    }

    public UsuarioConsulta find(String id) {
        if (head == null) {
            return null;
        } else {
            Nodo curr = head;

            while (curr != null) {
                if (curr.usuario.getId().equals(id))
                    return curr.usuario;
                curr = curr.next;
            }

            return null;
        }
    }
}
