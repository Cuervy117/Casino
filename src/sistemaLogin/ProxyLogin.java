package sistemaLogin;

import java.io.IOException;

public class ProxyLogin implements Login {
    private Login login;

    public ProxyLogin(Login login) {
        this.login = login;
    }

    @Override
    public boolean autenticar(String usuario, String contraseña) throws IOException {
        System.out.println("Iniciando autenticación");

        boolean autenticado = login.autenticar(usuario, contraseña);

        if (!autenticado) throw new IOException("Error: Usuario o contraseña incorrectos.");
        System.out.println("Usuario autenticado correctamente.");
        return autenticado;
    }
}
