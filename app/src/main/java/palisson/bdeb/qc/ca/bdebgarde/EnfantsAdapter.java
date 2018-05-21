package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Context;
import android.support.annotation.*;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;


public class EnfantsAdapter extends ArrayAdapter<Enfant> {

    ArrayList<Enfant> listeEnfants;
    /**
     * Constructeur
     * @param context Le contexte
     * @param listeEnfants La liste d'enfants
     */
    public EnfantsAdapter(Context context, ArrayList<Enfant> listeEnfants){
        super(context,0, listeEnfants); this.listeEnfants = (ArrayList<Enfant>)listeEnfants.clone();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Enfant enfant = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.enfant_list, parent, false);

        TextView tvNom = (TextView) convertView.findViewById(R.id.tvNom);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        tvNom.setText(enfant.getPrenom());
        tvDate.setText(enfant.stringDateNaissance());

        return convertView;
    }

    /**
     * Actualise la liste d'enfants
     * @param nouveauxEnfants Les nouveaux enfants
     */
    public void actualiser(ArrayList<Enfant> nouveauxEnfants)
    {
        super.clear();
        for(Enfant enfant : nouveauxEnfants) {super.add(enfant); Log.i("Nouvel enfant", enfant.toString());}
        this.listeEnfants = (ArrayList<Enfant>)nouveauxEnfants.clone();
    }

    /**
     * Utilisé pour la barre de recherche
     * @param texte Un texte qui doit être commun à tous les enfants dans la liste
     */
    public void restreindreRecherche(String texte)
    {
        super.clear();
        if(texte.isEmpty() )
            for(Enfant enfant : listeEnfants) super.add(enfant);


        else {
            ArrayList<String> mots = new ArrayList<>();
            String motActuel = "";
            char caractere;
            for (int i = 0; i < texte.length(); i++) {
                caractere = texte.charAt(i);
                if (!Character.isLetter(caractere)) {
                    if (caractere == ' ' && motActuel.length() != 0) {
                        mots.add(motActuel);
                        motActuel = "";
                    }
                } else
                    motActuel += Character.toLowerCase(caractere);


            }
            if (motActuel.length() != 0)
                mots.add(motActuel);

            else if(mots.size() == 0)
            {
                for(Enfant enfant : listeEnfants) super.add(enfant);
                return;
            }


            for (Enfant enfant : listeEnfants) {

                boolean ok = false;
                String nom = enfant.getNom().toLowerCase();
                String prenom = enfant.getPrenom().toLowerCase();
                for (String mot : mots)
                    ok |= (nom.contains(mot) || prenom.contains(mot));

                if (ok)
                    super.add(enfant);
            }
        }

    }

}
