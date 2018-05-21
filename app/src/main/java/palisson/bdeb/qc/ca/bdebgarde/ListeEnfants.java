package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


public class ListeEnfants extends AppCompatActivity {

    public static final String MESSAGE_EXTRA = "MESSAAAAGE";

    private ListView listView;
    EnfantsAdapter enfantAdapter;

    /**
     * Pour créer l'activité
     * @param savedInstanceState 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_parent);

        enfantAdapter = new EnfantsAdapter(this, CampDeJour.interfaceClient.getListeEnfants());

        listView = (ListView) findViewById(R.id.listName);
        listView.setOnItemClickListener(listeClickListener);
        listView.setAdapter(enfantAdapter);
        final EditText texteRecherche = (EditText)findViewById(R.id.texteRecherche);
        texteRecherche.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                enfantAdapter.restreindreRecherche(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }



    /**
     * Appelé lorsque l'activité est détruite
     */
    @Override
    protected void onDestroy()
    {

        super.onDestroy();

    }
    
    // Lorsqu'un enfant est sélectionné
    private AdapterView.OnItemClickListener listeClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id){

            Enfant enfant = (Enfant) enfantAdapter.getItem(position);
            Log.i("POSITION-1", ""+position);


            Intent intent = new Intent(ListeEnfants.this, VueEnfant.class);
            intent.putExtra(MESSAGE_EXTRA, CampDeJour.interfaceClient.getListeEnfants().indexOf(enfant));
            Log.i("POSITION", ""+CampDeJour.interfaceClient.getListeEnfants().indexOf(enfant));

            startActivity(intent);
        }
    };

    /**
     * Conséquence du fait d'appuyer sur le bouton "actualiser"
     * @param v 
     */
    public void actualiser(View v) {

        CampDeJour.interfaceClient.identification(CampDeJour.interfaceClient.getMotDePasse(), new Runnable() {
            @Override
            public void run() {
                enfantAdapter.actualiser(CampDeJour.interfaceClient.getListeEnfants()); enfantAdapter.restreindreRecherche(((EditText)findViewById(R.id.texteRecherche)).getText().toString());
            }
        }, null);

    }


}
