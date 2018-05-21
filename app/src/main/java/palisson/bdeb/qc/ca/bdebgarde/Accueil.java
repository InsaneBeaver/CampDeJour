package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class Accueil extends AppCompatActivity {

    /**
     * Sur la création
     * @param savedInstanceState 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        if(CampDeJour.interfaceClient == null)
            try {

                CampDeJour.interfaceClient = new InterfaceClient();
                InterfaceClient.initialiserClient(this);
            }
            catch(Exception e) {}

    }

    /**
     * Sert à faire la transition vers la page de log-in
     * @param v 
     */
    public void loginParent(View v){
        Intent loginParent = new Intent(this, PageDeLogin.class);
        startActivity(loginParent);
    }

}
