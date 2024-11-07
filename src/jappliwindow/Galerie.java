package jappliwindow;

import java.util.ArrayList;

public class Galerie {
    private ArrayList<Image> images;

    // Constructeur
    public Galerie() {
        this.images = new ArrayList<>();
    }

    // Méthode pour ajouter une image à la galerie
    public void ajouterImage(Image image) {
        images.add(image);
    }

    // Méthode pour retirer une image de la galerie
    public void retirerImage(Image image) {
        images.remove(image);
    }

    // Méthode pour retirer une image par son titre
    public void retirerImageParTitre(String titre) {
        images.removeIf(image -> image.getTitre().equalsIgnoreCase(titre));
    }

    // Méthode pour obtenir toutes les images de la galerie
    public ArrayList<Image> getImages() {
        return images;
    }

    // Méthode pour vérifier si une image est présente dans la galerie
    public boolean contientImage(Image image) {
        return images.contains(image);
    }

    // Méthode pour obtenir le nombre total d'images dans la galerie
    public int getNombreImages() {
        return images.size();
    }

    // Méthode pour rechercher des images par mots-clés
    public ArrayList<Image> rechercherImages(String motCle) {
        ArrayList<Image> resultats = new ArrayList<>();
        for (Image image : images) {
            if (image.getTitre().toLowerCase().contains(motCle.toLowerCase()) || 
                image.getDescription().toLowerCase().contains(motCle.toLowerCase())) {
                resultats.add(image);
            }
        }
        return resultats;
    }

    // Méthode pour afficher le contenu de la galerie
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Galerie :\n");
        if (images.isEmpty()) {
            sb.append("Aucune image dans la galerie.\n");
        } else {
            for (Image image : images) {
                sb.append(image.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}