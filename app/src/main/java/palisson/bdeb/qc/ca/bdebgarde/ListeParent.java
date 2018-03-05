package palisson.bdeb.qc.ca.bdebgarde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListeParent extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_parent);

        String[] liste0 = {"Hugo","Guillaume","Olivier"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, liste0);

        ListView listView = (ListView) findViewById(R.id.listName);
        listView.setAdapter(adapter);
    }


}
