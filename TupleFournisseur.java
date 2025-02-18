package apce.tuples;


public class TupleFournisseur {
    public String nom;
    public String courriel;
    public String adresse;
    public int idFournisseur;


    public TupleFournisseur(String nom, String courriel, String adresse,int idFournisseur){
        this.nom = nom;
        this.courriel = courriel;
        this.adresse = adresse;
        this.idFournisseur = idFournisseur;

    }

    public TupleFournisseur(){}

    public void setNom(String nom){
        this.nom=nom;
    }


    public void setAdresse(String adresse){
        this.adresse=adresse;
    }
    public void setCourriel(String courriel){
        this.courriel=courriel;
    }
    public void setIdFournisseur(int idFournisseur){this.idFournisseur=idFournisseur;}
}
