package fr.biocoop.poissonnerie.repositories.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

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

    @Enumerated(EnumType.STRING)
    private TypePoisson type;

    @JoinTable(name = "poissons_zones_de_peche",
            joinColumns = @JoinColumn(name = "code_poisson"),
            inverseJoinColumns = @JoinColumn(name = "code_zone_peche")
    )
    @ManyToMany
    private List<ZonePeche> zonesDePeche;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEspece() {
        return espece;
    }

    public void setEspece(String espece) {
        this.espece = espece;
    }

    public String getNomScientifique() {
        return nomScientifique;
    }

    public void setNomScientifique(String nomScientifique) {
        this.nomScientifique = nomScientifique;
    }

    public Date getDateDebutVente() {
        return dateDebutVente;
    }

    public void setDateDebutVente(Date dateDebutVente) {
        this.dateDebutVente = dateDebutVente;
    }

    public Date getDateFinVente() {
        return dateFinVente;
    }

    public void setDateFinVente(Date dateFinVente) {
        this.dateFinVente = dateFinVente;
    }

    public TypePoisson getType() {
        return type;
    }

    public void setType(TypePoisson type) {
        this.type = type;
    }

    public List<ZonePeche> getZonesDePeche() {
        return zonesDePeche;
    }

    public void setZonesDePeche(List<ZonePeche> zonesDePeche) {
        this.zonesDePeche = zonesDePeche;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poisson poisson = (Poisson) o;
        return code == poisson.code &&
                Objects.equals(espece, poisson.espece) &&
                Objects.equals(nomScientifique, poisson.nomScientifique) &&
                Objects.equals(dateDebutVente, poisson.dateDebutVente) &&
                Objects.equals(dateFinVente, poisson.dateFinVente) &&
                type == poisson.type &&
                Objects.equals(zonesDePeche, poisson.zonesDePeche);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, espece, nomScientifique, dateDebutVente, dateFinVente, type, zonesDePeche);
    }

    @Override
    public String toString() {
        return "Poisson{" +
                "code=" + code +
                ", espece='" + espece + '\'' +
                ", nomScientifique='" + nomScientifique + '\'' +
                ", dateDebutVente=" + dateDebutVente +
                ", dateFinVente=" + dateFinVente +
                ", type=" + type +
                ", zonesDePeche=" + zonesDePeche +
                '}';
    }
}
