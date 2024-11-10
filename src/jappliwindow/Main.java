package jappliwindow;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {
    private static Statistiques statistiques = new Statistiques();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection conn = DatabaseManager.getConnection()) {
            if (conn != null) {
                System.out.println("Connexion réussie à la base de données MySQL !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            e.printStackTrace();
        }

        boolean continuer = true;
        while (continuer) {
            afficherMenu();
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le saut de ligne

            switch (choix) {
                case 1:
                    ajouterUtilisateur();
                    break;
                case 2:
                    ajouterImage();
                    break;
                case 3:
                    statistiques.afficherStatistiques();
                    break;
                case 4:
                    afficherImagesUtilisateur();
                    break;
                case 5:
                    retirerImageUtilisateur();
                    break;
                case 6:
                    gererUtilisateurs();
                    break;
                case 7:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
        scanner.close();
    }

    private static void afficherMenu() {
        System.out.println("BIENVENUE SUR LE DASHBOARD DE NOTRE SITE DE TELECHARGEMENTS D'IMAGE EN LIGNE ");
        System.out.println("1. Ajouter un utilisateur");
        System.out.println("2. Ajouter une image");
        System.out.println("3. Afficher les statistiques");
        System.out.println("4. Afficher les images d'un utilisateur");
        System.out.println("5. Retirer une image de la galerie d'un utilisateur");
        System.out.println("6. Gérer les utilisateurs (Admin)");
        System.out.println("7. Quitter");
        System.out.print("Choisissez une option : ");
    }

    private static void ajouterUtilisateur() {
        System.out.print("Entrez l'ID de l'utilisateur : ");
        int idUtilisateur = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Entrez le nom de l'utilisateur : ");
        String nom = scanner.nextLine();

        String sql = "INSERT INTO Utilisateur (idUtilisateur, nom) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUtilisateur);
            pstmt.setString(2, nom);
            pstmt.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }

    private static void ajouterImage() {
        System.out.print("Entrez l'ID de l'utilisateur qui ajoute l'image : ");
        int idUtilisateur = scanner.nextInt();
        scanner.nextLine();
        
        if (!utilisateurExiste(idUtilisateur)) {
            System.out.println("Utilisateur non trouvé.");
            return;
        }

        System.out.print("Entrez le nom du fichier de l'image : ");
        String nomFichier = scanner.nextLine();
        System.out.print("Entrez le titre de l'image : ");
        String titre = scanner.nextLine();
        System.out.print("Entrez la description de l'image : ");
        String description = scanner.nextLine();

        String sql = "INSERT INTO Image (idUtilisateur, nomFichier, titre, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUtilisateur);
            pstmt.setString(2, nomFichier);
            pstmt.setString(3, titre);
            pstmt.setString(4, description);
            pstmt.executeUpdate();
            System.out.println("Image ajoutée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'image : " + e.getMessage());
        }
    }

    private static void afficherImagesUtilisateur() {
        System.out.print("Entrez l'ID de l'utilisateur : ");
        int idUtilisateur = scanner.nextInt();
        scanner.nextLine();

        String sql = "SELECT * FROM Image WHERE idUtilisateur = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUtilisateur);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String titre = rs.getString("titre");
                String description = rs.getString("description");
                System.out.println("Titre : " + titre + ", Description : " + description);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des images : " + e.getMessage());
        }
    }

    private static void retirerImageUtilisateur() {
        System.out.print("Entrez le titre de l'image à retirer : ");
        String titreImage = scanner.nextLine();

        String sql = "DELETE FROM Image WHERE titre = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, titreImage);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Image retirée avec succès !");
            } else {
                System.out.println("Image non trouvée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'image : " + e.getMessage());
        }
    }
    
 // Méthode pour vérifier le mot de passe de l'administrateur
    private static boolean verifierMotDePasseAdmin(String nomAdmin, String motDePasseSaisi) {
        String sql = "SELECT motDePasse FROM Administrateur WHERE nomAdmin = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomAdmin);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String motDePasseStocke = rs.getString("motDePasse");

                // Comparer le mot de passe saisi avec celui stocké (ici on suppose qu'il est haché)
                if (motDePasseStocke.equals(motDePasseSaisi)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification du mot de passe : " + e.getMessage());
        }
        return false;
    }


    private static void gererUtilisateurs() {
        System.out.print("Entrez le nom de l'administrateur : ");
        String nomAdmin = scanner.nextLine();

        System.out.print("Entrez le mot de passe pour accéder à la gestion des utilisateurs : ");
        String motDePasseSaisi = scanner.nextLine();

        // Vérifier le nom et le mot de passe (ici on compare avec ceux stockés dans la base de données)
        if (!verifierMotDePasseAdmin(nomAdmin, motDePasseSaisi)) {
            System.out.println("Nom ou mot de passe incorrect. Accès refusé.");
            return;
        }

        System.out.println("Options de gestion des utilisateurs :");
        System.out.println("1. Lister tous les utilisateurs");
        System.out.println("2. Supprimer un utilisateur");
        System.out.print("Choisissez une option : ");
        int choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1:
                listerUtilisateurs();
                break;
            case 2:
                supprimerUtilisateur();
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }

    

    private static void listerUtilisateurs() {
        String sql = "SELECT * FROM Utilisateur";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("idUtilisateur");
                String nom = rs.getString("nom");
                System.out.println("ID : " + id + ", Nom : " + nom);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
    }

    private static void supprimerUtilisateur() {
        System.out.print("Entrez l'ID de l'utilisateur à supprimer : ");
        int idUtilisateur = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM Utilisateur WHERE idUtilisateur = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUtilisateur);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Utilisateur supprimé avec succès !");
            } else {
                System.out.println("Utilisateur non trouvé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }

    private static boolean utilisateurExiste(int idUtilisateur) {
        String sql = "SELECT * FROM Utilisateur WHERE idUtilisateur = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUtilisateur);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de l'utilisateur : " + e.getMessage());
        }
        return false;
    }
}
