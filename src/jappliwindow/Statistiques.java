package jappliwindow;



public class Statistiques {
    private int nombreImagesTelechargees;
    private int nombreUtilisateurs;

    public Statistiques() {
        this.nombreImagesTelechargees = 0;
        this.nombreUtilisateurs = 0;
    }

    public void ajouterImageTelechargee(Image image) {
        nombreImagesTelechargees++;
    }

    public void retirerImageTelechargee(Image image) {
        if (nombreImagesTelechargees > 0) {
            nombreImagesTelechargees--;
        }
    }

    public void ajouterUtilisateur() {
        nombreUtilisateurs++;
    }

    public void afficherStatistiques() {
        System.out.println("Statistiques :");
        System.out.println("Nombre total d'images téléchargées : " + nombreImagesTelechargees);
        System.out.println("Nombre total d'utilisateurs : " + nombreUtilisateurs);
    }

    public int getNombreImagesTelechargees() {
        return nombreImagesTelechargees;
    }

    public int getNombreUtilisateurs() {
        return nombreUtilisateurs;
    }
}