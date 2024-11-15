package sistemaLogin;

import usuario.Usuario;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseDeDatos implements Serializable, Login{
    private Map<String, Usuario> usuarios;
    private static BaseDeDatos singleton;

    public BaseDeDatos() {
        usuarios = new HashMap<>();
    }

    public static BaseDeDatos getBaseDeDatos() {
        if( singleton == null ) {
            // intentar leer base de datos
            // si hay error crear una nueva
            singleton = new BaseDeDatos();
            return singleton;
        } else {
            return singleton;
        }
    }

    public Map<String, Usuario> getUsuarios(){
        return getBaseDeDatos().usuarios;
    }

    @Override
    public boolean autenticar(String nombreDeUsuario, String contraseña) throws IOException {
        if (nombreDeUsuario == null || contraseña == null) throw new IOException("Usuario ó contraseña invalidos");
        int contraseñaAutentica = BaseDeDatos.getBaseDeDatos().getUsuarios().get(nombreDeUsuario).getContraseña();
        if(contraseñaAutentica == contraseña.hashCode()){
            return true;
        } else {
            return false;
        }
    }

    public static void agregarUsuario(Usuario usuario){
        BaseDeDatos.getBaseDeDatos().getUsuarios().put(usuario.getNombre(), usuario);
    }
}
