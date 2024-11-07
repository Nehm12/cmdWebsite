package jappliwindow;

public class Utilisateur {
    private int idUtilisateur;
    private Galerie galerie;
    private String nom;
    private String email;
    private boolean suspendu; 
 
    
    // Getter pour suspendu
     public boolean isSuspendu() {
         return suspendu;
     }

    public Utilisateur(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    // Constructeur
    public Utilisateur(int idUtilisateur, String nom) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.galerie = new Galerie(); // Chaque utilisateur a une galerie personnelle
    }

    // Getters et Setters
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getnom() {
        return nom;
    }
    
    public String getEmail() {
        return email; // Assurez-vous que 'email' est un attribut de la classe
    }

    public String getNom() {
        return nom; // Assurez-vous que 'nom' est un attribut de la classe
    }

    // Méthode pour suspendre l'utilisateur
    public void suspendre() {
        this.suspendu = true; // Change l'état de l'utilisateur à suspendu
        System.out.println("L'utilisateur " + nom + " a été suspendu.");
    }

    public void setNomUtilisateur(String nom) {
        this.nom = nom;
    }

    public Galerie getGalerie() {
        return galerie;
    }

    // Méthode pour ajouter une image à la galerie de l'utilisateur
    public void ajouterImageAGalerie(Image image) {
        this.galerie.ajouterImage(image);
    }

    // Méthode pour retirer une image de la galerie de l'utilisateur
    public void retirerImageDeGalerie(Image image) {
        this.galerie.retirerImage(image);
    }

    // Méthode pour afficher toutes les images dans la galerie
    public void afficherImagesDansGalerie() {
        System.out.println("Images dans la galerie de " + nom + ":");
        System.out.println(galerie);
    }

    // Méthode pour afficher les informations de l'utilisateur
    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + idUtilisateur +
                ", nom='" + nom + '\'' +
                ", galerie=" + galerie +
                '}';
    }
}