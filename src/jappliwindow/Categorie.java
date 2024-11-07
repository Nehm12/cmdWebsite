package jappliwindow;

import java.util.ArrayList;

public class Categorie {
    private String nomCategorie;
    private ArrayList<Image> images;

    public Categorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
        this.images = new ArrayList<>();
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

 

    public void retirerImage(Image image) {
        images.remove(image);
    }
    
    public ArrayList<Image> getImages() {
        return images;
    }

    // Ajoute une image à la catégorie
    public void ajouterImage(Image image) {
        images.add(image);
    }

    // Supprime une image de la catégorie
    public void supprimerImage(Image image) {
        if (images.remove(image)) {
            System.out.println("L'image " + image.getNomFichier() + " a été supprimée de la catégorie " + nomCategorie);
        } else {
            System.out.println("L'image " + image.getNomFichier() + " n'est pas trouvée dans la catégorie " + nomCategorie);
        }
    }

    // Recherche d'images par mots-clés dans la catégorie
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

    // Affiche toutes les images de la catégorie
    public void afficherImages() {
        if (images.isEmpty()) {
            System.out.println("Aucune image dans la catégorie " + nomCategorie);
        } else {
            System.out.println("Images dans la catégorie " + nomCategorie + ":");
            for (Image image : images) {
                System.out.println("- " + image.getNomFichier() + ": " + image.getDescription());
            }
        }
    }
}