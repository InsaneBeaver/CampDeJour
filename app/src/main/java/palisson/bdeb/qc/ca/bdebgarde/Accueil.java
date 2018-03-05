package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        ((CampDeJour) this.getApplication()).getListeEnfants();
    }


    public void loginParent(View v){
        Intent loginParent = new Intent(this, ParentVueEnfantActivity.class);
        startActivity(loginParent);
    }
}
