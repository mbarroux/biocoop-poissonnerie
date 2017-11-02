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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public ZonePeche getParent() {
        return parent;
    }

    public void setParent(ZonePeche parent) {
        this.parent = parent;
    }

    public List<ZonePeche> getSousZones() {
        return sousZones;
    }

    public void setSousZones(List<ZonePeche> sousZones) {
        this.sousZones = sousZones;
    }

    public List<Poisson> getPoissons() {
        return poissons;
    }

    public void setPoissons(List<Poisson> poissons) {
        this.poissons = poissons;
    }

    @Override
    public String toString() {
        return "ZonePeche{" +
                "code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
