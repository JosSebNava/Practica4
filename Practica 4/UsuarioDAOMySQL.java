import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOMySQL implements UsuarioDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/practica4";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crearUsuario(Usuario usuario) throws IOException, ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA)) {
            String sql = "INSERT INTO usuarios (nombre, apellido, correoElectronico, contrasena) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, usuario.getNombre());
                preparedStatement.setString(2, usuario.getApellido());
                preparedStatement.setString(3, usuario.getCorreoElectronico());
                preparedStatement.setString(4, usuario.getContrasena());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IOException("Error al crear el usuario en la base de datos.", e);
        }
    }

    @Override
    public Usuario obtenerUsuarioPorId(int id) throws IOException, ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA)) {
            String sql = "SELECT * FROM usuarios WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapUsuario(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new IOException("Error al obtener el usuario de la base de datos.", e);
        }

        return null;
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() throws IOException, ClassNotFoundException {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA)) {
            String sql = "SELECT * FROM usuarios";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        usuarios.add(mapUsuario(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IOException("Error al obtener la lista de usuarios de la base de datos.", e);
        }

        return usuarios;
    }

    private Usuario mapUsuario(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String nombre = resultSet.getString("nombre");
        String apellido = resultSet.getString("apellido");
        String correoElectronico = resultSet.getString("correoElectronico");
        String contrasena = resultSet.getString("contrasena");

        return new Usuario(id, nombre, apellido, correoElectronico, contrasena);
    }
}
