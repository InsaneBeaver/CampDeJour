package palisson.bdeb.qc.ca.bdebgarde;

import android.util.Log;

import java.util.ArrayList;

public class ListeEnfants {
    private ArrayList<Enfant> liste;

    public ListeEnfants()
    {
        liste = new ArrayList<>();
    }


    public int getPosition(int id)
    {

        int iMin = 0;
        int iMax = liste.size();
        int iMilieu = 0;
        int idMilieu;
        boolean marche = false;
        while(!marche && iMin != iMax)
        {
            iMilieu = (iMin + iMax)/2;
            idMilieu = liste.get(iMilieu).getId();
            if(idMilieu > id)
                iMax = iMilieu;
            else if(idMilieu < id)
            {
                if(iMin == iMilieu) break;
                iMin = iMilieu;
            }
            else
                marche = true;


        }
        return iMilieu;

    }

    public void addEnfant(Enfant enfant){
        liste.add(enfant);
    }

    public int indexOf(Enfant enfant){
        return liste.indexOf(enfant);
    }

    public ArrayList<Enfant> getListe(){
        ArrayList<Enfant> fakeList = liste;

        return fakeList;
    }

    public Enfant getEnfantParID(int id)
    {
        int pos = getPosition(id);
        Enfant enfant = liste.get(pos);
        if(enfant.getId() == id) return enfant;
        else return null;
    }

    public void ajouterEnfant(Enfant nouvelEnfant)
    {
        if(liste.size() == 0) {liste.add(nouvelEnfant); return; }
        int pos = getPosition(nouvelEnfant.getId());
        Enfant enfant = liste.get(pos);

        if(liste.size() == 0) {liste.add(enfant); return; }
        if(enfant.getId() == nouvelEnfant.getId())
        {
            liste.remove(pos);
            liste.add(pos, nouvelEnfant);
        }
        else
            liste.add(pos + 1, nouvelEnfant);

    }

    public Enfant getEnfant(int pos)
    {
        return liste.get(pos);
    }


}
