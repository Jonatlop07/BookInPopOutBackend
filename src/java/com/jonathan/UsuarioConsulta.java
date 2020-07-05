/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jonathan;

/**
 *
 * @author Jonathan López
 */
public class UsuarioConsulta {
    private String id;
    private String nombre;
    private String documento;
    private String fechaNacimiento;
    private String correo;
    private String discapacitado;

    public UsuarioConsulta() {
        this.id = "";
        this.nombre = "";
        this.documento = "";
        this.fechaNacimiento = "";
        this.correo = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String esDiscapacitado() {
        return discapacitado;
    }

    public void setDiscapacitado(String discapacitado) {
        this.discapacitado = discapacitado;
    }
}
