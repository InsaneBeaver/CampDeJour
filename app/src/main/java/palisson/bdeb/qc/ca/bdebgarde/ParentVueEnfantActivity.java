package palisson.bdeb.qc.ca.bdebgarde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ParentVueEnfantActivity extends AppCompatActivity {

    private Enfant enfant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_parent_enfant);



        int position = this.getIntent().getIntExtra(ListeParent.MESSAGE_EXTRA, 0);
        enfant = CampDeJour.getListeEnfants().getEnfant(position);

        EditText foo = (EditText)findViewById(R.id.champNom);
        foo.setText("Foobar");
    }

    private void setTextViews(){

    }
}
