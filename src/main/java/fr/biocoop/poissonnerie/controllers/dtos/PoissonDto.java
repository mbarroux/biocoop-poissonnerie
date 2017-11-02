package fr.biocoop.poissonnerie.controllers.dtos;

import fr.biocoop.poissonnerie.repositories.entities.TypePoisson;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class PoissonDto {

    private final static DateFormat monthFormatter = new SimpleDateFormat("MMM", Locale.FRENCH);

    public static class Builder {
        private int code;
        private String espece;
        private String nomScientifique;
        private Date dateDebutVente;
        private Date dateFinVente;
        private TypePoisson type;
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

        public Builder setType(TypePoisson type) {
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
        this.dateDebutVente = monthFormatter.format(builder.dateDebutVente);
        this.dateFinVente = monthFormatter.format(builder.dateFinVente);
        this.type = builder.type.getLibelle().toLowerCase();
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

    private boolean commercialisationPossible(Date dateDebut, Date dateFin) {
        LocalDate debutJour = LocalDate.now();
        LocalDate dateDebutVente = ((java.sql.Date) dateDebut).toLocalDate();
        LocalDate dateFinVente = ((java.sql.Date) dateFin).toLocalDate();

        return (debutJour.isAfter(dateDebutVente) || debutJour.isEqual(dateDebutVente))
                && (debutJour.isBefore(dateFinVente) || debutJour.isEqual(dateFinVente));
    }
}
