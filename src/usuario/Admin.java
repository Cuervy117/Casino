/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usuario;

/**
 *
 * @author David
 */
import java.util.ArrayList;
import java.util.List;
import metodosDePago.Cartera;
//Caretaker en memento. Encargado de guardar los estados de usuarios
public class Admin extends Usuario {
    private List<Usuario> usuarios; // Lista de usuarios registrados en el casino

    // Aquí sugiero que el admin tenga un atributo de la siguiente forma: private GestorUsuarios <identificador>;
    public Admin(String id, String nombre, String correoElectronico, Cartera cartera, String contraseña) {
        super(id, nombre, correoElectronico, cartera, contraseña); // Iniciamos con saldo 0 y como administrador
        this.usuarios = new ArrayList<>();
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuario " + usuario.getNombre() + " agregado exitosamente.");
    }

    public void eliminarUsuario(Usuario usuario) {
        if (usuarios.remove(usuario)) {
            System.out.println("Usuario " + usuario.getNombre() + " eliminado exitosamente.");
        } else {
            System.out.println("El usuario no fue encontrado.");
        }
    }

    public Usuario buscarUsuarioPorId(String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        System.out.println("Usuario con ID " + id + " no encontrado.");
        return null;
    }

    // Hace falta lógica de validación por contraseña

    public void mostrarUsuarios() {
        System.out.println("Lista de usuarios registrados:");
        for (Usuario usuario : usuarios) {
            usuario.mostrarInformacion();
        }
    }

    public void activarCuentaUsuario(Usuario usuario) {
        usuario.reactivarCuenta();
        System.out.println("La cuenta del usuario " + usuario.getNombre() + " ha sido activada.");
    }

    public void desactivarCuentaUsuario(Usuario usuario) {
        usuario.desactivarCuenta();
        System.out.println("La cuenta del usuario " + usuario.getNombre() + " ha sido desactivada.");
    }

    public void otorgarBonificacion(Usuario usuario, double cantidad) {
        // Metodo pendiente
        System.out.println("Se ha otorgado una bonificación de $" + cantidad + " al usuario " + usuario.getNombre() + ".");
    }
}
