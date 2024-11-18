/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usuario;

import java.io.Serializable;
import memento.Memento;
import metodosDePago.Cartera;

/**
 *
 * @author David
 */
// Originator en memento
public class Usuario implements Serializable{
    private String id;               
    private String nombre;          
    private String correoElectronico; 
    private Cartera cartera;
    private boolean cuentaActiva;   
    private int contraseña;

    public Usuario(){

    }

    public Usuario(String id, String nombre, String correoElectronico, Cartera cartera, String contraseña) {
        this.id = id;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.cartera = cartera;
        this.cuentaActiva = true;
        this.contraseña = contraseña.hashCode();
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña.hashCode();
    }

    public void setCartera(Cartera cartera) {
        this.cartera = cartera;
    }

    public void setCuentaActiva(boolean cuentaActiva) {
        this.cuentaActiva = cuentaActiva;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public Cartera getCartera() {
        return cartera;
    }

    public int getContraseña(){
        return contraseña;
    }

    public boolean isCuentaActiva() {
        return cuentaActiva;
    }

    // *************************************************************** 
    // Método pendiente para actualizar saldo a Cartera
    // ***************************************************************

    public void desactivarCuenta() {
        this.cuentaActiva = false;
        System.out.println("La cuenta ha sido desactivada.");
    }

    public void reactivarCuenta() {
        this.cuentaActiva = true;
        System.out.println("La cuenta ha sido reactivada.");
    }
    // Modificación realizada: Adición de bloque try- catch
    public void pagar(double cantidad){
        try {
            cartera.realizarPago(cantidad);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public void mostrarInformacion() {
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Correo Electrónico: " + correoElectronico);
        System.out.println("Cuenta Activa: " + (cuentaActiva ? "Sí" : "No"));
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", cartera=" + cartera +
                ", cuentaActiva=" + cuentaActiva +
                ", contraseña=" + contraseña +
                '}';
    }
    // Crear y restaurar Mementos
    public Memento crearMemento() {
        return new Memento(cartera.getSaldo(), cartera.getHistorial());
    }

    public void restaurarMemento(Memento memento) {
        this.getCartera().setSaldo(memento.getSaldo()); //this.saldo = memento.getSaldo();
        this.getCartera().setHistorial(memento.getHistorial());
    }
}

