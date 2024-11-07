package jappliwindow;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Categorie> categories = new ArrayList<>();
    private static Statistiques statistiques = new Statistiques();
    private static ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
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
        scanner.nextLine(); // Consommer le saut de ligne
        System.out.print("Entrez le nom de l'utilisateur : ");
        String nom = scanner.nextLine();

        Utilisateur utilisateur = new Utilisateur(idUtilisateur, nom);
        utilisateurs.add(utilisateur);
        System.out.println("Utilisateur ajouté avec succès !");
    }

    private static void ajouterImage() {
        System.out.print("Entrez l'ID de l'utilisateur qui ajoute l'image : ");
        int idUtilisateur = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne

        Utilisateur utilisateur = trouverUtilisateurParId(idUtilisateur);
        if (utilisateur == null) {
            System.out.println("Utilisateur non trouvé.");
            return;
        }

        System.out.print("Entrez le nom du fichier de l'image : ");
        String nomFichier = scanner.nextLine();
        System.out.print("Entrez le titre de l'image : ");
        String titre = scanner.nextLine();
        System.out.print("Entrez la description de l'image : ");
        String description = scanner.nextLine();
        System.out.print("Entrez le nom de la catégorie : ");
        String nomCategorie = scanner.nextLine();

        // Créer une nouvelle image
        Image image = new Image(nomFichier, titre, description);
        statistiques.ajouterImageTelechargee(image);

        // Ajouter l'image à la catégorie correspondante
        Categorie categorie = trouverOuCreerCategorie(nomCategorie);
        categorie.ajouterImage(image);

        // Ajouter l'image à la galerie de l'utilisateur
        utilisateur.ajouterImageAGalerie(image);

        System.out.println("Image ajoutée avec succès !");
    }

    private static void afficherImagesUtilisateur() {
        System.out.print("Entrez l'ID de l'utilisateur : ");
        int idUtilisateur = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne

        Utilisateur utilisateur = trouverUtilisateurParId(idUtilisateur);
        if (utilisateur == null) {
            System.out.println("Utilisateur non trouvé.");
            return;
        }

        utilisateur.afficherImagesDansGalerie();
    }

    private static void retirerImageUtilisateur() {
        System.out.print("Entrez l'ID de l'utilisateur : ");
        int idUtilisateur = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne

        Utilisateur utilisateur = trouverUtilisateurParId(idUtilisateur);
        if (utilisateur == null) {
            System.out.println("Utilisateur non trouvé.");
            return;
        }

        System.out.print("Entrez le titre de l'image à retirer : ");
        String titreImage = scanner.nextLine();
        Image imageARetirer = null;

        // Recherche de l'image à retirer
        for (Image image : utilisateur.getGalerie().getImages()) {
            if (image.getTitre().equals(titreImage)) {
                imageARetirer = image;
                break;
            }
        }

        if (imageARetirer != null) {
            utilisateur.retirerImageDeGalerie(imageARetirer);
            statistiques.retirerImageTelechargee(imageARetirer); // Retirer de la statistique si nécessaire
            Categorie categorie = trouverCategorieParImage(imageARetirer);
            if (categorie != null) {
                categorie.retirerImage(imageARetirer); // Retirer de la catégorie si nécessaire
            }
            System.out.println("Image retirée avec succès !");
        } else {
            System.out.println("Image non trouvée dans la galerie de l'utilisateur.");
        }
    }

    private static void gererUtilisateurs() {
        System.out.println("Options de gestion des utilisateurs :");
        System.out.println("1. Lister tous les utilisateurs");
        System.out.println("2. Supprimer un utilisateur");
        System.out.print("Choisissez une option : ");
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne

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
        System.out.println("Liste des utilisateurs :");
        for (Utilisateur utilisateur : utilisateurs) {
            System.out.println("ID: " + utilisateur.getIdUtilisateur() + ", Nom: " + utilisateur.getNom());
        }
    }

    private static void supprimerUtilisateur() {
        System.out.print("Entrez l'ID de l'utilisateur à supprimer : ");
        int idUtilisateur = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne

        Utilisateur utilisateurASupprimer = trouverUtilisateurParId(idUtilisateur);
        if (utilisateurASupprimer != null) {
            utilisateurs.remove(utilisateurASupprimer);
            System.out.println("Utilisateur supprimé avec succès !");
        } else {
            System.out.println("Utilisateur non trouvé.");
        }
    }

    private static Categorie trouverOuCreerCategorie(String nomCategorie) {
        for (Categorie categorie : categories) {
            if (categorie.getNomCategorie().equals(nomCategorie)) {
                return categorie;
            }
        }
        Categorie nouvelleCategorie = new Categorie(nomCategorie);
        categories.add(nouvelleCategorie);
        return nouvelleCategorie;
    }

    private static Utilisateur trouverUtilisateurParId(int idUtilisateur) {
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getIdUtilisateur() == idUtilisateur) {
                return utilisateur;
            }
        }
        return null; // Utilisateur non trouvé
    }

    private static Categorie trouverCategorieParImage(Image image) {
        for (Categorie categorie : categories) {
            if (categorie.getImages().contains(image)) {
                return categorie;
            }
        }
        return null; // Catégorie non trouvée
    }
}