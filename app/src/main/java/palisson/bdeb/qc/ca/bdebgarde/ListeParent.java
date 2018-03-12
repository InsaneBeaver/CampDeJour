package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.app.*;
import android.content.DialogInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ListeParent extends AppCompatActivity {

    public static final String MESSAGE_EXTRA = "MESSAAAAGE";

    private ListView listView;
    EnfantsAdapter enfantAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_parent);
        enfantAdapter = new EnfantsAdapter(this, CampDeJour.getEnfants(getIntent().getStringExtra(LoginParent.MESSAGE_MDP_PARENT)));

        listView = (ListView) findViewById(R.id.listName);
        listView.setOnItemClickListener(listeClickListener);
        listView.setAdapter(enfantAdapter);
    }

    private AdapterView.OnItemClickListener listeClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id){

            Enfant enfant = (Enfant) listView.getItemAtPosition(position);

            Intent intent = new Intent(ListeParent.this, ParentVueEnfantActivity.class);
            intent.putExtra(MESSAGE_EXTRA, CampDeJour.getListeEnfants().indexOf(enfant));

            startActivity(intent);
        }
    };



}
