package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;



public class ListeParent extends AppCompatActivity {

    public static final String MESSAGE_EXTRA = "MESSAAAAGE";

    private ListView listView;
    EnfantsAdapter enfantAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_parent);
        Toast.makeText(this, getIntent().getStringExtra(LoginParent.MESSAGE_MDP_PARENT), Toast.LENGTH_LONG).show();


        enfantAdapter = new EnfantsAdapter(this, CampDeJour.listeEnfants);

        listView = (ListView) findViewById(R.id.listName);
        listView.setOnItemClickListener(listeClickListener);
        listView.setAdapter(enfantAdapter);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        CampDeJour.listeEnfants.clear();
        CampDeJour.motDePasse = "";
    }

    private AdapterView.OnItemClickListener listeClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id){

            Enfant enfant = (Enfant) listView.getItemAtPosition(position);

            Intent intent = new Intent(ListeParent.this, ParentVueEnfantActivity.class);
            intent.putExtra(MESSAGE_EXTRA, CampDeJour.listeEnfants.indexOf(enfant));

            startActivity(intent);
        }
    };



}
