package apce.tuples;

import java.math.BigDecimal;

public class TupleProduit {
    public String nom;
    public BigDecimal prix;
    public BigDecimal coût;
    public String categorie;
    public int idProduit;
    public int idProducteur;



    public TupleProduit(){

    }

    public TupleProduit(String nom, BigDecimal prix, BigDecimal coût, String categorie,int idProduit,int idProducteur){
        this.nom = nom;
        this.prix = prix;
        this.coût = coût;
        this.categorie = categorie;
        this.idProducteur = idProducteur;
        this.idProduit = idProduit;

    }

    public void setNom(String nom){
        this.nom=nom;
    }

    public void setPrix(BigDecimal prix ){
        this.prix=prix;
    }
    public void setIdProduit(int idProduit ){
        this.idProduit=idProduit;
    }
    public void setCoût(BigDecimal cout){
        this.coût=cout;
    }
    public void setCategorie(String categorie){
        this.categorie=categorie;
    }
    public void setIdProducteur(int idProducteur){
        this.idProducteur=idProducteur;
    }



    //TODO - Votre implémentation ici
}
