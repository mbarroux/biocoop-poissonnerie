package fr.biocoop.poissonnerie.repositories.entities;

import java.util.Date;

@Entity
public class Poisson {

    @Id
    private int code;

    private String espece;

    "nom_scientifique"
    private String nomScientifique;

    "date_debut_vente"
    private Date dateDebutVente;

    "date_fin_vente"
    private Date dateFinVente;

    @Enumerated(EnumType.INT)
    private TypePoisson type;
}
