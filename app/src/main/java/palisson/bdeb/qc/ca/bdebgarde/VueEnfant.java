package palisson.bdeb.qc.ca.bdebgarde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.util.*;

public class VueEnfant extends AppCompatActivity {
    EditText nom;
    EditText age;
    EditText dateDeNaissance;
    CheckBox saitNager;
    ToggleButton etat;
    RadioButton boutonMasculin;
    RadioButton boutonFeminin;
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CANADA_FRENCH);
    private Enfant enfant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_parent_enfant);


        int position = this.getIntent().getIntExtra(ListeEnfants.MESSAGE_EXTRA, 0);

        enfant = CampDeJour.interfaceClient.getListeEnfants().get(position);

        nom = (EditText)findViewById(R.id.champNom);
        age = (EditText)findViewById(R.id.champAge);
        dateDeNaissance = (EditText)findViewById(R.id.champDateDeNaissance);
        saitNager = (CheckBox)findViewById(R.id.boutonSaitNager);
        etat = (ToggleButton)findViewById(R.id.boutonEtat);

        etat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!CampDeJour.interfaceClient.estConnecte())
                {
                    etat.setChecked(enfant.isEstPresent());
                    return;
                }

                CampDeJour.interfaceClient.setPresence(enfant, etat.isChecked(), new InterfaceClient.ChangerPresence() {
                    @Override
                    public void changerPresence(boolean presence) {
                        etat.setChecked(presence);
                    }
                });

            }
        });
        boutonFeminin = (RadioButton) findViewById(R.id.boutonFeminin);
        boutonMasculin = (RadioButton) findViewById(R.id.boutonMasculin);

        chargerEnfant(enfant);
    }


    private void chargerEnfant(Enfant enfant)
    {
        nom.setText(enfant.getNom() + ", " + enfant.getPrenom());
        age.setText(""+enfant.getAge());
        saitNager.setChecked(enfant.isSaitNager());
        if(enfant.getSexe() == Enfant.Sexe.F) boutonFeminin.setChecked(true);
        else boutonMasculin.setChecked(true);
        etat.setChecked(enfant.isEstPresent());
        dateDeNaissance.setText(enfant.stringDateNaissance());

    }




}
