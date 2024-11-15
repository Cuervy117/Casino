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

public class Admin extends Usuario {
    private List<Usuario> usuarios; // Lista de usuarios registrados en el casino

    public Admin(String id, String nombre, String correoElectronico) {
        super(id, nombre, correoElectronico, null); // Iniciamos con saldo 0 y como administrador
        this.usuarios = new ArrayList<>();
    }

    // Método para agregar un usuario al sistema
    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuario " + usuario.getNombre() + " agregado exitosamente.");
    }

    // Método para eliminar un usuario del sistema
    public void eliminarUsuario(Usuario usuario) {
        if (usuarios.remove(usuario)) {
            System.out.println("Usuario " + usuario.getNombre() + " eliminado exitosamente.");
        } else {
            System.out.println("El usuario no fue encontrado.");
        }
    }

    // Método para buscar un usuario por su ID
    public Usuario buscarUsuarioPorId(String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        System.out.println("Usuario con ID " + id + " no encontrado.");
        return null;
    }

    // Método para mostrar todos los usuarios registrados
    public void mostrarUsuarios() {
        System.out.println("Lista de usuarios registrados:");
        for (Usuario usuario : usuarios) {
            usuario.mostrarInformacion();
        }
    }

    // Método para activar la cuenta de un usuario
    public void activarCuentaUsuario(Usuario usuario) {
        usuario.reactivarCuenta();
        System.out.println("La cuenta del usuario " + usuario.getNombre() + " ha sido activada.");
    }

    // Método para desactivar la cuenta de un usuario
    public void desactivarCuentaUsuario(Usuario usuario) {
        usuario.desactivarCuenta();
        System.out.println("La cuenta del usuario " + usuario.getNombre() + " ha sido desactivada.");
    }

    // Método para otorgar bonificación a un usuario
    public void otorgarBonificacion(Usuario usuario, double cantidad) {
        // Metodo pendiente
        System.out.println("Se ha otorgado una bonificación de $" + cantidad + " al usuario " + usuario.getNombre() + ".");
    }
}
