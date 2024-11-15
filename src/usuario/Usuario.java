/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usuario;

/**
 *
 * @author David
 */
public class Usuario {
    private String id;               
    private String nombre;          
    private String correoElectronico; 
    private Cartera cartera;            
    private boolean cuentaActiva;   

    public Usuario(String id, String nombre, String correoElectronico, Cartera cartera) {
        this.id = id;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.cartera = cartera;
        this.cuentaActiva = true;
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

    // Método para reactivar la cuenta
    public void reactivarCuenta() {
        this.cuentaActiva = true;
        System.out.println("La cuenta ha sido reactivada.");
    }

    // Método para mostrar información del usuario
    public void mostrarInformacion() {
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Correo Electrónico: " + correoElectronico);
        System.out.println("Cuenta Activa: " + (cuentaActiva ? "Sí" : "No"));
    }
}

