package fr.biocoop.poissonnerie.controllers.dtos;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class ZonePecheDto {

    public static class Builder {
        private String code;
        private String libelle;
        private List<ZonePecheDto> sousZones;

        public ZonePecheDto build() {
            return new ZonePecheDto(this);
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setLibelle(String libelle) {
            this.libelle = libelle;
            return this;
        }

        public Builder setSousZones(List<ZonePecheDto> sousZones) {
            this.sousZones = sousZones;
            return this;
        }
    }

    private final String code;
    private final String libelle;
    private final List<ZonePecheDto> sousZones;

    private ZonePecheDto(Builder builder) {
        this.code = builder.code;
        this.libelle = builder.libelle;
        this.sousZones = builder.sousZones != null ? unmodifiableList(builder.sousZones) : emptyList();
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    public List<ZonePecheDto> getSousZones() {
        return sousZones;
    }

    public boolean isNiveau2() {
        return getSousZones().isEmpty();
    }
}
