package fr.biocoop.poissonnerie.repositories.entities;

public enum TypePoisson {
    POISSON(1, "Poisson"),
    CRUSTACE(2, "Crustacé");

    private int code;
    private String libelle;

    TypePoisson(int code, String libelle){
        this.code = code;
        this.libelle = libelle;
    }

    public int getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }
}
