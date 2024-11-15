package sistemaLogin;

import java.io.IOException;

public interface Login {
    boolean autenticar(String nombreDeUsuario, String Contraseña) throws IOException;
}
