package palisson.bdeb.qc.ca.bdebgarde;

/**
 * Created by 1645720 on 2018-03-05.
 */

public enum Mois {
    JANVIER(1, "Janvier"),
    FEVRIER(2, "Février"),
    MARS(3, "Mars"),
    AVRIL(4, "Avril"),
    MAI(5, "Mai"),
    JUIN(6, "Juin"),
    JUILLET(7, "Juillet"),
    AOUT(8, "Août"),
    SEPTEMBRE(9, "Septembre"),
    OCTOBRE(10, "Octobre"),
    NOVEMBRE(11, "Novembre"),
    DECEMBRE(12, "Décembre");




    private final int numero;
    private final String nom;

    Mois(int numero, String nom){
        this.numero = numero;
        this.nom = nom;

    }

    public int getNumero(){
        return this.numero;
    }

    public String getNom(){
        
    }



}
