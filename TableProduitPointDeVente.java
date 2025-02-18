package apce.tables;

import apce.bdd.Connexion;
import apce.tuples.TupleFournisseur;
import apce.tuples.TuplePointDeVente;
import apce.tuples.TupleProducteur;
import apce.tuples.TupleProduit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TableProduitPointDeVente extends GestionTables{

    private final PreparedStatement stmtInsert;
    private final PreparedStatement  stmtDelete;
    private final PreparedStatement  stmtDeletee;
    private final PreparedStatement  stmtDelet;
    private final PreparedStatement  stmtGete;
    private final PreparedStatement  stmtListePo;
    private final PreparedStatement  stmtListeProdct;
        public TableProduitPointDeVente(Connexion cx)  throws SQLException {
            super(cx);
            this.stmtInsert = cx.getConnection().prepareStatement(
                    "INSERT INTO produitpointdevente (idProduit,idPointdeVente) VALUES (?, ?)"

            );
            this.stmtDelete =cx.getConnection().prepareStatement(
                    "DELETE FROM produitpointdevente WHERE idpointdevente = ?");
            this.stmtDeletee =cx.getConnection().prepareStatement(
                    "DELETE FROM produitpointdevente WHERE idproduit = ?");
            this.stmtGete= cx.getConnection().prepareStatement(
                    "SELECT idProduit, idpointdevente  FROM produitpointdevente WHERE idProduit=? AND idpointdevente=? "
            );
            this.stmtDelet =cx.getConnection().prepareStatement("DELETE FROM produitpointdevente WHERE idproduit = ? AND idpointdevente=?"
            );
            this.stmtListeProdct = cx.getConnection().prepareStatement(
                    "SELECT p.* FROM produit p " +
                            "JOIN produitpointdevente pp ON p.idproduit = pp.idproduit  " +
                            "JOIN pointdevente po ON po.idpointdevente = pp.idpointdevente " +
                            "WHERE po.nom_pointdevente = ?");

            this.stmtListePo = cx.getConnection().prepareStatement("SELECT f.* FROM pointdevente f " +
                    "JOIN produitpointdevente pa ON f.idpointdevente = pa.idpointdevente " +
                    "WHERE pa.idProduit = ?");
        }
   public boolean  existe(int id1, int id2) throws SQLException{
       stmtGete.setInt(1, id1);
       stmtGete.setInt(2, id2);
       ResultSet resultSet = stmtGete.executeQuery();
       boolean existe = resultSet.next();
       resultSet.close();
       return existe;
    }
    public void ajouterProduitPointDeVente(int id1, int id2) throws SQLException{

            stmtInsert.setInt(1, id1);
            stmtInsert.setInt(2, id2);
            stmtInsert.executeUpdate();
        }

        public List<TupleProduit> getPointsFromProduit(String pointdevente)throws SQLException {


            stmtListeProdct.setString(1,pointdevente);
            ResultSet rest = stmtListeProdct.executeQuery();
            List<TupleProduit> produitpoint= new LinkedList<>();
            while(rest.next()){
                TupleProduit produit = new TupleProduit(
                        rest.getString("nom_Produit"),

                        rest.getBigDecimal("Prix") ,
                        rest.getBigDecimal("coût"),
                        rest.getString("categorie"),
                        rest.getInt("idproduit"),
                        rest.getInt("idProducteur")
                );


                produitpoint.add(produit);

            }
            rest.close();
            return produitpoint;
    }

    public List<TuplePointDeVente> getPointDeVFromProduit(int idProduit)throws SQLException {
        //TODO votre code ici
        stmtListePo.setInt(1,idProduit);
        ResultSet rest = stmtListePo.executeQuery();
        List<TuplePointDeVente> pointDeVentees= new LinkedList<>();
        while(rest.next()){
            TuplePointDeVente pointVte = new TuplePointDeVente(
                    rest.getString("nom_PointDeVente"),
                    rest.getString("courriel") ,
                    rest.getString("adresse_postale"),
                    rest.getInt("idPointDeVente"));


            pointDeVentees.add(pointVte);

        }
        rest.close();
        return pointDeVentees;

    }


    public void supprimerAssociationsProduits(int idp1) throws SQLException{

        stmtDelete.setInt(1,idp1);
        stmtDelete.executeUpdate();
    }
    public void supprimerProduitPointDeVente(int id1,int id2) throws SQLException{
        stmtDelet.setInt(1,id1);
        stmtDelet.setInt(2,id2);
        stmtDelet.executeUpdate();
    }
    public void supprimerAssociationsPproduits(int idproduit) throws SQLException{

        stmtDeletee.setInt(1,idproduit);
        stmtDeletee.executeUpdate();
    }
    }




