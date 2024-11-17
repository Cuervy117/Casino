package memento;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import usuario.*;

public class GestorUsuarios {
    private Map<String, Usuario> usuarios;
    private File archivoUsuarios;
    private static String paquete;

    public GestorUsuarios(String rutaArchivo) {
        archivoUsuarios = new File(rutaArchivo);
        usuarios = cargarUsuarios();
        paquete = obtenerDirectorio(rutaArchivo);
    }

    public void registrarUsuario(Usuario nuevoUsuario) {
        if (usuarios.containsKey(nuevoUsuario.getNombre())) {
            throw new IllegalArgumentException("El usuario ya existe.");
        }
        usuarios.put(nuevoUsuario.getNombre(), nuevoUsuario);
        guardarUsuarios();
    }

    public Usuario autenticarUsuario(String nombreUsuario, String contrasena) {
        Usuario usuario = usuarios.get(nombreUsuario);
        
        if (usuario != null && usuario.getContraseña() == contrasena.hashCode()) {
            return usuario;
        }
        throw new IllegalArgumentException("Usuario o contraseña incorrectos.");
    }

    public void guardarEstadoUsuario(String nombreUsuario) {
        Usuario usuario = usuarios.get(nombreUsuario);
        if (usuario != null) {
            Memento memento = usuario.crearMemento();
            guardarMemento(nombreUsuario, memento);
        }
    }

    public void restaurarEstadoUsuario(String nombreUsuario) {
        Usuario usuario = usuarios.get(nombreUsuario);
        if (usuario != null) {
            Memento memento = cargarMemento(nombreUsuario);
            if (memento != null) {
                usuario.restaurarMemento(memento);
            }
        }
    }

    private void guardarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoUsuarios))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Usuario> cargarUsuarios() {
        if (archivoUsuarios.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoUsuarios))) {
                return (Map<String, Usuario>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>();
    }

    private void guardarMemento(String nombreUsuario, Memento memento) {
        File archivoMemento = new File(paquete +nombreUsuario + "_estado.dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoMemento))) {
            oos.writeObject(memento);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Memento cargarMemento(String nombreUsuario) {
        File archivoMemento = new File(paquete + nombreUsuario + "_estado.dat");
        if (archivoMemento.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoMemento))) {
                return (Memento) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    private static String obtenerDirectorio(String rutaArchivo) {
        Path ruta = Paths.get(rutaArchivo);
        Path directorioPadre = ruta.getParent();
        if (directorioPadre != null) {
            return directorioPadre.toString() + "/";
        }
        return "";
    }
}


