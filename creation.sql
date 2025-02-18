
CREATE TABLE PRODUCTEUR (


                            Nom_Producteur    VARCHAR(250) UNIQUE NOT NULL ,
                            Courriel           VARCHAR(250) NOT NULL,
                            Adresse_Postale    VARCHAR(250) NOT NULL,
                            Nombre_Employes    INTEGER NOT NULL,
                            idProducteur      SERIAL PRIMARY KEY
);



CREATE TABLE POINTDEVENTE (

                              Nom_PointdeVente      VARCHAR(250) UNIQUE NOT NULL ,
                              Courriel                VARCHAR(250) NOT NULL,
                              Adresse_Postale         VARCHAR(250) NOT NULL,
                              idPointdeVente        SERIAL PRIMARY KEY
);


CREATE TABLE FOURNISSEUR (
                             Nom_Fournisseur             VARCHAR(250) UNIQUE NOT NULL,
                             Courriel                  VARCHAR(250) NOT NULL,
                             Adresse_Postale            VARCHAR(250) NOT NULL,
                             idFournisseur               SERIAL PRIMARY KEY
);


CREATE TABLE PRODUIT (

                         Nom_Produit                      VARCHAR(250) UNIQUE NOT NULL ,
                         Prix                             DECIMAL(10, 2) NOT NULL,
                         Coût                             DECIMAL(10, 2) NOT NULL,
                         Categorie                        VARCHAR(250) NOT NULL,
                         idProducteur                  INTEGER NOT NULL,
                         idProduit                       SERIAL PRIMARY KEY ,

                         CONSTRAINT refproducteur00 FOREIGN KEY (idProducteur) REFERENCES PRODUCTEUR(idProducteur)
);


CREATE TABLE PRODUITFOURNISSEUR (
                                    idFournisseur       INTEGER NOT NULL,
                                    idProduit          INTEGER NOT NULL,
                                    CONSTRAINT clePrimaire00 PRIMARY KEY (idFournisseur, idProduit),
                                    CONSTRAINT reffournisseur00 FOREIGN KEY (idFournisseur) REFERENCES FOURNISSEUR(idFournisseur),
                                    CONSTRAINT refProduit00 FOREIGN KEY (idProduit) REFERENCES PRODUIT(idProduit)
);


CREATE TABLE PRODUITPOINTDEVENTE (
                                     idProduit           INTEGER NOT NULL ,
                                     idPointdeVente      INTEGER NOT NULL ,
                                     CONSTRAINT clePrimaire01 PRIMARY KEY (idProduit, idPointDeVente),
                                     CONSTRAINT refProduit01 FOREIGN KEY (idProduit) REFERENCES PRODUIT(idProduit) ,
                                     CONSTRAINT refPoint_de_Vente00 FOREIGN KEY (idPointdeVente) REFERENCES POINTDEVENTE(idPointdeVente)
);


CREATE TABLE PRODUCTEURFOURNISSEUR (
                                       idProducteur          INTEGER NOT NULL ,
                                       idFournisseur          INTEGER NOT NULL ,
                                       CONSTRAINT clePrimaire02 PRIMARY KEY (idProducteur, idFournisseur),
                                       CONSTRAINT refproducteur01 FOREIGN KEY (idProducteur) REFERENCES PRODUCTEUR(idProducteur) ,
                                       CONSTRAINT reffournisseur01 FOREIGN KEY (idFournisseur) REFERENCES FOURNISSEUR(idFournisseur)
);

CREATE TABLE PRODUCTEURPOINTDEVENTE (
                                        idProducteur             INTEGER NOT NULL ,
                                        idPointdeVente          INTEGER NOT NULL ,
                                        CONSTRAINT clePrimaire03 PRIMARY KEY (idProducteur, idPointdeVente),
                                        CONSTRAINT refproducteur02 FOREIGN KEY (idProducteur) REFERENCES PRODUCTEUR(idProducteur) ,
                                        CONSTRAINT refpoint_de_vente01 FOREIGN KEY (idPointdeVente) REFERENCES POINTDEVENTE(idPointdeVente)
);
