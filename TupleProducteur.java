package apce.tuples;

public class TupleProducteur {
    public String nom_producteur;
    public int nbEmploi;
    public String courriel;
    public String adresse;
    public int idProducteur;

    public TupleProducteur(){

    }

    public TupleProducteur(String nom, String courriel, String adresse,int nbEmploi, int idProducteur){
        this.nom_producteur = nom;
        this.nbEmploi = nbEmploi;
        this.courriel = courriel;
        this.adresse = adresse;
        this.idProducteur = idProducteur;
    }

    public void setNom(String nom){
        this.nom_producteur=nom;
    }

    public void setNbEmploi(int nbEmploi ){
        this.nbEmploi=nbEmploi;
    }
    public void setAdresse(String adresse){
        this.adresse=adresse;
    }
    public void setCourriel(String courriel){
        this.courriel=courriel;
    }
    public int getIdProducteur() {
        return idProducteur;
    }

    public void setIdProducteur(int idProducteur) {
        this.idProducteur = idProducteur;
    }


    public void setInt(int idProduit) {

    }
}
