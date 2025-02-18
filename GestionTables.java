package apce.tables;

import apce.bdd.Connexion;

public abstract class GestionTables {
    protected final Connexion cx;

    protected GestionTables(Connexion cx) {
        this.cx = cx;
    }

    public Connexion getConnexion() {
        return cx;
    }
}
