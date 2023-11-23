import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UsuarioDAO usuarioDAOArchivo = new UsuarioDAOArchivo();
        UsuarioDAO usuarioDAOMySQL = new UsuarioDAOMySQL();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Menú de Usuario ===");
            System.out.println("1. Crear Usuario (Archivo)");
            System.out.println("2. Crear Usuario (MySQL)");
            System.out.println("3. Obtener Usuario por ID (Archivo)");
            System.out.println("4. Obtener Usuario por ID (MySQL)");
            System.out.println("5. Obtener Todos los Usuarios (Archivo)");
            System.out.println("6. Obtener Todos los Usuarios (MySQL)");
            System.out.println("7. Salir");
            System.out.print("Ingrese su opción: ");

            int opcionMenu = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea después de nextInt()

            switch (opcionMenu) {
                case 1:
                    crearUsuario(usuarioDAOArchivo, scanner);
                    break;
                case 2:
                    crearUsuario(usuarioDAOMySQL, scanner);
                    break;
                case 3:
                    obtenerUsuarioPorId(usuarioDAOArchivo, scanner);
                    break;
                case 4:
                    obtenerUsuarioPorId(usuarioDAOMySQL, scanner);
                    break;
                case 5:
                    obtenerTodosLosUsuarios(usuarioDAOArchivo);
                    break;
                case 6:
                    obtenerTodosLosUsuarios(usuarioDAOMySQL);
                    break;
                case 7:
                    System.out.println("Saliendo...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void crearUsuario(UsuarioDAO usuarioDAO, Scanner scanner) {
        System.out.println("\n=== Crear Usuario ===");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Correo Electrónico: ");
        String correoElectronico = scanner.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        try {
            Usuario nuevoUsuario = new Usuario(0, nombre, apellido, correoElectronico, contrasena);
            usuarioDAO.crearUsuario(nuevoUsuario);
            System.out.println("Usuario creado con éxito.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al crear el usuario: " + e.getMessage());
        }
    }

    private static void obtenerUsuarioPorId(UsuarioDAO usuarioDAO, Scanner scanner) {
        System.out.println("\n=== Obtener Usuario por ID ===");

        System.out.print("Ingrese el ID del usuario: ");
        int id = scanner.nextInt();

        try {
            Usuario usuario = usuarioDAO.obtenerUsuarioPorId(id);
            if (usuario != null) {
                System.out.println("Usuario encontrado:\n" + usuario);
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al obtener el usuario: " + e.getMessage());
        }
    }

    private static void obtenerTodosLosUsuarios(UsuarioDAO usuarioDAO) {
        System.out.println("\n=== Obtener Todos los Usuarios ===");

        try {
            List<Usuario> usuarios = usuarioDAO.obtenerTodosLosUsuarios();
            if (!usuarios.isEmpty()) {
                System.out.println("Lista de Usuarios:");
                for (Usuario usuario : usuarios) {
                    System.out.println(usuario);
                }
            } else {
                System.out.println("No hay usuarios registrados.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al obtener la lista de usuarios: " + e.getMessage());
        }
    }
}
