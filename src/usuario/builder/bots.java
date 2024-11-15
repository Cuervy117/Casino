package usuario.builder;

import usuario.Usuario;

import java.io.Serializable;

public class bots implements builder {
    Usuario usuario;

    public Usuario build(){
        Usuario product = usuario;
        reset();
        return product;
    }
    
    public void reset(){
        usuario = new Usuario();
    }
}
