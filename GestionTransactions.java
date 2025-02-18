package apce.gestion;

import apce.bdd.Connexion;

public abstract class GestionTransactions {
    protected final Connexion cx;

    protected GestionTransactions(Connexion cx) {
        this.cx = cx;
    }
}
