import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOArchivo implements UsuarioDAO {
    private static final String NOMBRE_ARCHIVO = "usuarios.txt";

    @Override
    public void crearUsuario(Usuario usuario) throws IOException, ClassNotFoundException {
        List<Usuario> usuarios = leerUsuarios();

        // Asignar un nuevo ID al usuario
        usuario.setId(generarNuevoId(usuarios));

        usuarios.add(usuario);
        guardarUsuarios(usuarios);
    }

    @Override
    public Usuario obtenerUsuarioPorId(int id) throws IOException, ClassNotFoundException {
        List<Usuario> usuarios = leerUsuarios();

        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }

        return null;
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() throws IOException, ClassNotFoundException {
        return leerUsuarios();
    }

    private List<Usuario> leerUsuarios() throws IOException, ClassNotFoundException {
        List<Usuario> usuarios = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NOMBRE_ARCHIVO))) {
            usuarios = (List<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, se creará al guardar el primer usuario.
        }

        return usuarios;
    }

    private void guardarUsuarios(List<Usuario> usuarios) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOMBRE_ARCHIVO))) {
            oos.writeObject(usuarios);
        }
    }

    private int generarNuevoId(List<Usuario> usuarios) {
        return usuarios.isEmpty() ? 1 : usuarios.get(usuarios.size() - 1).getId() + 1;
    }
}
