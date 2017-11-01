package fr.biocoop.poissonnerie.repositories.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "poissons")
public class Poisson {

    @Id
    private int code;

    private String espece;

    @Column(name = "nom_scientifique")
    private String nomScientifique;

    @Column(name = "date_debut_vente")
    @Temporal(TemporalType.DATE)
    private Date dateDebutVente;

    @Column(name = "date_fin_vente")
    @Temporal(TemporalType.DATE)
    private Date dateFinVente;

    @Enumerated(EnumType.ORDINAL)
    private TypePoisson type;

    @JoinTable(name = "poissons_zones_de_peche",
            joinColumns = @JoinColumn(name = "code_poisson"),
            inverseJoinColumns = @JoinColumn(name = "code_zone_peche")
    )
    @ManyToMany
    private List<ZonePeche> zonesDePeche;
}
