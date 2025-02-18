package apce.bdd;

import apce.CollantException;

import java.sql.*;

/**
 * Gestionnaire d'une connexion avec une BD relationnelle via JDBC.<br><br>
 * 
 * Cette classe ouvre une connexion avec une BD via JDBC.<br>
 * La méthode serveursSupportes() indique les serveurs supportés.<br>
 * <pre>
 * Pré-condition
 *   Le driver JDBC approprié doit être accessible.
 * 
 * Post-condition
 *   La connexion est ouverte en mode autocommit false et sérialisable, 
 *   (s'il est supporté par le serveur).
 * </pre>
 * <br>
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * @author Marc Frappier - Université de Sherbrooke
 * @version Version 2.0 - 13 novembre 2004
 *
 * @author Vincent Ducharme - Université de Sherbrooke
 * @version Version 3.0 - 21 mai 2016
 *
 * @author Frédéric Bergeron - Université de Sherbrooke
 * @version Version 4.1 - 10 juin 2022
 */
public class Connexion implements IConnexion
{
    private final Connection conn;

    /**
     * Ouverture d'une connexion en mode autocommit false et sérialisable (si
     * supporté)
     * 
     * @param serveur Le type de serveur SQL à utiliser (Valeur : local, dinf).
     * @param bd      Le nom de la base de données sur le serveur.
     * @param user    Le nom d'utilisateur à utiliser pour se connecter à la base de données.
     * @param pass    Le mot de passe associé à l'utilisateur.
     */
    public Connexion(String serveur, String bd, String user, String pass)
            throws CollantException, SQLException
    {
        Driver d;
        try
        {
            d = (Driver)Class.forName("org.postgresql.Driver").newInstance();
            DriverManager.registerDriver(d);
            
            if (serveur.equals("local"))
            {
                conn = DriverManager.getConnection("jdbc:postgresql:" + bd, user, pass);
            }
            else if (serveur.equals("dinf"))
            {
                conn = DriverManager.getConnection("jdbc:postgresql://bd-info2.dinf.usherbrooke.ca:5432/" + bd + "?ssl=true&sslmode=require", user, pass);
            }
            else
            {
                throw new CollantException(serveursSupportes());
            }

            // Mise en mode de commit manuel
            conn.setAutoCommit(false);

            // Mise en mode sérialisable, si possible
            // (plus haut niveau d'integrité pour l'accès concurrent aux données)
            DatabaseMetaData dbmd = conn.getMetaData();
            if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE))
            {
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                System.out.println("Ouverture de la connexion en mode sérialisable :\n"
                        + "Connecté sur la BD postgreSQL "
                        + bd + " avec l'utilisateur " + user);
            }
            else
            {
                System.out.println("Ouverture de la connexion en mode read committed (default) :\n"
                        + "Connecté sur la BD postgreSQL "
                        + bd + " avec l'utilisateur " + user);
            }
        }
        catch (SQLException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
            throw new CollantException("JDBC Driver non instancié");
        }
    }

    /**
     * Fermeture d'une connexion
     */
    public void fermerConnexion() throws SQLException
    {
        conn.rollback();
        conn.close();
        System.out.println("Connexion fermée " + conn);
    }

    /**
     * Démarre une transaction
     */
    public void demarreTransaction() {
        //Le concept n'existe pas avec PostgreSQL. La première requête démarre automatiquement une transaction
    }

    /**
     * Persiste la transaction courante. En SQL, cela correspond à un commit
     */
    public void executeTransaction() throws SQLException
    {
        conn.commit();
    }

    public void setIsolationReadCommited() throws SQLException
    {
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
    }

    /**
     * Annule la transaction courante. En SQL cela correspond à un rollback
     */
    public void annuleTransaction() throws SQLException
    {
        conn.rollback();
    }

    /**
     * Retourne la Connection JDBC
     */
    public Connection getConnection()
    {
        return conn;
    }

    public void setAutoCommit(boolean m) throws SQLException
    {
        conn.setAutoCommit(false);
    }

    /**
     * Retourne la liste des serveurs supportés par ce gestionnaire de
     * connexions
     */
    public static String serveursSupportes()
    {
        return "local : PostgreSQL installé localement\n"+
                "dinf  : PostgreSQL installé sur les serveurs du département";
    }
}
