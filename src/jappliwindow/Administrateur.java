package jappliwindow;

import java.util.ArrayList;


public class Administrateur extends Utilisateur {
    private Statistiques statistiques;

    public Administrateur(String nom, String email) {
        super(nom, email);
        this.statistiques = new Statistiques(); // Initialisation des statistiques
    }

    // Méthode pour modérer une image
    public void modererImage(Image image, boolean approuver) {
        if (approuver) {
            image.approuver();
            statistiques.ajouterImageTelechargee(image); // Ajout à la liste des images téléchargées
            System.out.println("L'image " + image.getNomFichier() + " a été approuvée.");
        } else {
            image.rejeter();
            System.out.println("L'image " + image.getNomFichier() + " a été rejetée.");
        }
    }

    // Méthode pour afficher les statistiques
    public void afficherStatistiques() {
        statistiques.afficherStatistiques();
    }

    // Méthode pour gérer les utilisateurs
    public void gestionUtilisateurs(ArrayList<Utilisateur> utilisateurs) {
        System.out.println("Gestion des utilisateurs :");
        for (Utilisateur utilisateur : utilisateurs) {
            System.out.println("Nom: " + utilisateur.getNom() + ", Email: " + utilisateur.getEmail());
        }
        // Logique pour ajouter, modifier, supprimer des utilisateurs peut être implémentée ici
    }

    // Méthode pour ajouter un utilisateur
    public void ajouterUtilisateur(ArrayList<Utilisateur> utilisateurs, Utilisateur nouvelUtilisateur) {
        utilisateurs.add(nouvelUtilisateur);
        System.out.println("Utilisateur ajouté : " + nouvelUtilisateur.getNom());
    }

    // Méthode pour supprimer un utilisateur
    public void supprimerUtilisateur(ArrayList<Utilisateur> utilisateurs, Utilisateur utilisateur) {
        utilisateurs.remove(utilisateur);
        System.out.println("Utilisateur supprimé : " + utilisateur.getNom());
    }

    // Méthode pour suspendre un utilisateur
    public void suspendreUtilisateur(Utilisateur utilisateur) {
        utilisateur.suspendre(); // Supposons que Utilisateur a une méthode suspendre
        System.out.println("Utilisateur suspendu : " + utilisateur.getNom());
    }
}