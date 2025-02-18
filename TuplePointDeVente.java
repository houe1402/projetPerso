package apce.tuples;

public class TuplePointDeVente {

    public String nom;
    public String courriel;
    public String adresse;
    public int idpointdevente;

    public TuplePointDeVente(){}
    public TuplePointDeVente(String nom, String courriel, String adresse,int idpointdevente){
        this.nom = nom;
        this.courriel = courriel;
        this.adresse = adresse;
        this.idpointdevente = idpointdevente;

    }



    public void setNom(String nom){
        this.nom=nom;
    }


    public void setAdresse(String adresse){
        this.adresse=adresse;
    }
    public void setCourriel(String courriel){
        this.courriel=courriel;
    }
    public void setidpointdevente(int idpointDeVente){this.idpointdevente=idpointDeVente;}
}















    //TODO - Votre code ici

