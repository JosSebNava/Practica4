import java.io.IOException;
import java.util.List;

public interface UsuarioDAO {
    void crearUsuario(Usuario usuario) throws IOException, ClassNotFoundException;
    Usuario obtenerUsuarioPorId(int id) throws IOException, ClassNotFoundException;
    List<Usuario> obtenerTodosLosUsuarios() throws IOException, ClassNotFoundException;
}
