package jappliwindow;

public class Image {
    private String nomFichier;
    private String titre;
    private String description;
    private boolean estApprouve; // Indique si l'image est approuvée ou non

    public Image(String nomFichier, String titre, String description) {
        this.nomFichier = nomFichier;
        this.titre = titre;
        this.description = description;
        this.estApprouve = false; // Par défaut, l'image n'est pas approuvée
    }

    // Getter pour nomFichier
    public String getNomFichier() {
        return nomFichier;
    }

    // Setter pour nomFichier
    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    // Getter pour titre
    public String getTitre() {
        return titre;
    }

    // Setter pour titre
    public void setTitre(String titre) {
        this.titre = titre;
    }

    // Getter pour description
    public String getDescription() {
        return description;
    }

    // Setter pour description
    public void setDescription(String description) {
        this.description = description;
    }

    // Méthode pour approuver l'image
    public void approuver() {
        this.estApprouve = true;
    }

    // Méthode pour rejeter l'image
    public void rejeter() {
        this.estApprouve = false;
    }

    // Méthode pour vérifier si l'image est approuvée
    public boolean estApprouve() {
        return estApprouve;
    }

    // Méthode pour afficher les détails de l'image
    public String afficherDetails() {
        return "Titre : " + titre + "\n" +
               "Nom du fichier : " + nomFichier + "\n" +
               "Description : " + description + "\n" +
               "Statut d'approbation : " + (estApprouve ? "Approuvée" : "Non approuvée");
    }

    @Override
    public String toString() {
        return titre + " (" + nomFichier + ")";
    }
}