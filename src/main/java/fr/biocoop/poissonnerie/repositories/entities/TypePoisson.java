package fr.biocoop.poissonnerie.repositories.entities;

public enum TypePoisson {
    POISSON(1),
    CRUSTACE(2);

    private int code;

    TypePoisson(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
