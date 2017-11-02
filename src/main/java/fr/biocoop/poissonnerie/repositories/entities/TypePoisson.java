package fr.biocoop.poissonnerie.repositories.entities;

public enum TypePoisson {
    POISSON("Poisson"),
    CRUSTACE("Crustacé");

    private String libelle;

    TypePoisson(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
