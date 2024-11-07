import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseManager {
    // Déclaration des constantes pour l'URL, le nom d'utilisateur et le mot de passe
    private static final String URL = "jdbc:mysql://localhost:3306/site_Image";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Méthode pour établir la connexion
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Méthode principale pour tester la connexion
    public static void main(String[] args) {
        // Tester la connexion en utilisant la méthode getConnection
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Connexion réussie !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
