package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Date;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        ((CampDeJour) this.getApplication()).getListeEnfants();

       // Enfant hugo = new Enfant("Hugo", false, Enfant.Sexe.M, 0, "Foo", true, new Date(101, 4, 21), 76);
        //Enfant guillaume = new Enfant("Guillaume", new Date(99, 8, 18));
        //Enfant olivier = new Enfant("Olivier", new Date(99, 6, 23));

        //CampDeJour.addEnfant(hugo);
       // CampDeJour.addEnfant(guillaume);
        //CampDeJour.addEnfant(olivier);
    }


    public void loginParent(View v){
        Intent loginParent = new Intent(this, LoginParent.class);
        startActivity(loginParent);
    }
}
