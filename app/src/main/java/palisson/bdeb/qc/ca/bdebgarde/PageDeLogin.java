package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.view.Gravity;

import com.google.zxing.integration.android.IntentIntegrator;


public class PageDeLogin extends AppCompatActivity  implements View.OnClickListener  {

    private IntentIntegrator qrScan;
    private EditText passwordConfirmer;
    private Runnable surMauvaisMotDePasse = new Runnable() {
        @Override
        public void run() {
            CampDeJour.afficherMessage(PageDeLogin.this, "Mot de passe invalide", "Erreur", "Compris");
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CampDeJour.interfaceClient.supprimerSession();
        this.passwordConfirmer = (EditText) findViewById(R.id.passwordText);


        setContentView(R.layout.activity_login_parent);
        ((EditText)findViewById(R.id.passwordText)).setGravity(Gravity.CENTER_HORIZONTAL);
        qrScan = new IntentIntegrator(this);


    }

/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //si pas information
            if (result.getContents() == null) {
                Toast.makeText(this, "Résultat pas trouvé", Toast.LENGTH_LONG).show();
            } else {
                ouvrirListe(result.getContents());
            }
        }
    }
*/

    public void finishLogin(View v)
    {
        passwordConfirmer = (EditText) findViewById(R.id.passwordText);

        String motDePasse = passwordConfirmer.getText().toString();
        if(!motDePasse.isEmpty())
             CampDeJour.interfaceClient.identification(motDePasse,
                     new Runnable() {
                         @Override
                         public void run() {
                             completerIdentification();
                         }},
                     surMauvaisMotDePasse );


    }


    private void completerIdentification()
    {
        passwordConfirmer.setText("");
        try
        {

            Intent listeParent = new Intent(this, ListeEnfants.class);
            startActivity(listeParent);
        }
        catch(Exception e) {}



    }
  @Override
    public void onClick(View v) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }

}
