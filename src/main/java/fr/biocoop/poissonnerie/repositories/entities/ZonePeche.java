package fr.biocoop.poissonnerie.repositories.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "zones_de_peche")
public class ZonePeche {

    @Id
    private String code;

    private String libelle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code_zone_parent")
    private ZonePeche parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private List<ZonePeche> sousZones;

    @ManyToMany(mappedBy = "zonesDePeche")
    private List<Poisson> poissons;
}
