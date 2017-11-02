package fr.biocoop.poissonnerie.controllers.dtos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.ofNullable;

public class PoissonDto {

    private final static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public static class Builder {
        private int code;
        private String espece;
        private String nomScientifique;
        private Date dateDebutVente;
        private Date dateFinVente;
        private String type;
        private List<ZonePecheDto> zonesDePeche;

        public PoissonDto build() {
            return new PoissonDto(this);
        }

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setEspece(String espece) {
            this.espece = espece;
            return this;
        }

        public Builder setNomScientifique(String nomScientifique) {
            this.nomScientifique = nomScientifique;
            return this;
        }

        public Builder setDateDebutVente(Date dateDebutVente) {
            this.dateDebutVente = dateDebutVente;
            return this;
        }

        public Builder setDateFinVente(Date dateFinVente) {
            this.dateFinVente = dateFinVente;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setZonesDePeche(List<ZonePecheDto> zonesDePeche) {
            this.zonesDePeche = zonesDePeche;
            return this;
        }
    }

    private final int code;
    private final String espece;
    private final String nomScientifique;
    private final String dateDebutVente;
    private final String dateFinVente;
    private final String type;
    private final List<ZonePecheDto> zonesDePeche;
    private final boolean commercialisationPossible;

    private PoissonDto(Builder builder) {
        this.code = builder.code;
        this.espece = builder.espece;
        this.nomScientifique = builder.nomScientifique;
        this.dateDebutVente = ofNullable(builder.dateDebutVente).map(dateFormatter::format).orElse(null);
        this.dateFinVente = ofNullable(builder.dateFinVente).map(dateFormatter::format).orElse(null);
        this.type = builder.type;
        this.zonesDePeche = builder.zonesDePeche != null ? unmodifiableList(builder.zonesDePeche) : emptyList();
        this.commercialisationPossible = commercialisationPossible(builder.dateDebutVente, builder.dateFinVente);
    }

    public int getCode() {
        return code;
    }

    public String getEspece() {
        return espece;
    }

    public String getNomScientifique() {
        return nomScientifique;
    }

    public String getDateDebutVente() {
        return dateDebutVente;
    }

    public String getDateFinVente() {
        return dateFinVente;
    }

    public String getType() {
        return type;
    }

    public List<ZonePecheDto> getZonesDePeche() {
        return zonesDePeche;
    }

    public boolean isCommercialisationPossible() {
        return commercialisationPossible;
    }

    private boolean commercialisationPossible(Date dateDebutVente, Date dateFinVente) {
        Instant debutJour = ZonedDateTime.now().toInstant();
        Instant dateDebutVenteInstant = dateDebutVente.toInstant();
        Instant dateFinVenteInstant = dateFinVente.toInstant();

        return (debutJour.isAfter(dateDebutVenteInstant) || debutJour.equals(dateDebutVenteInstant))
                && (debutJour.isBefore(dateFinVenteInstant) || debutJour.equals(dateFinVenteInstant));
    }
}
