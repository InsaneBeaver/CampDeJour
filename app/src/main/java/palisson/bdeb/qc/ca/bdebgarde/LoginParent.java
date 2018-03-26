package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.view.Gravity;
import android.app.*;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


public class LoginParent extends AppCompatActivity  implements View.OnClickListener  {

    private IntentIntegrator qrScan;
    public final static String MESSAGE_MDP_PARENT = "MDPPARENT";
    EditText passwordConfirmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.passwordConfirmer = (EditText) findViewById(R.id.passwordText);
        Toast.makeText(this, "TEXTE " + this.passwordConfirmer, Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_login_parent);
        ((EditText)findViewById(R.id.passwordText)).setGravity(Gravity.CENTER_HORIZONTAL);
        qrScan = new IntentIntegrator(this);

    }


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

    public void finishLogin(View v)
    {
        passwordConfirmer = (EditText) findViewById(R.id.passwordText);
        ouvrirListe(passwordConfirmer.getText().toString());
    }

    private String reponse = "";
    boolean estEnAttente = false;
    private final static double TIMEOUT = 2000;

    private void ouvrirListe(String motdepasse)
    {
        if(estEnAttente) return;
        estEnAttente = true;
        reponse = "";
        CampDeJour.client.envoyerMessage("liste " + motdepasse.trim(), new Client.SurReception() {
            @Override
            public void operation(String reponse) {
                LoginParent.this.reponse = reponse;
            }
        });
        double t1 = System.currentTimeMillis();
        try {
            while (reponse.isEmpty() && (System.currentTimeMillis() - t1 < TIMEOUT)) {
                new Thread().sleep(5);
            }
        }
        catch(Exception e) {}

        estEnAttente = false;
        if(reponse.isEmpty()) return;
        if(reponse.equals("[]"))
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoginParent.this);

            dlgAlert.setMessage("Mot de passe invalide. ");
            dlgAlert.setTitle("Erreur");
            dlgAlert.setPositiveButton("Compris", null);
            dlgAlert.create().show();

        }
        else
        {
            passwordConfirmer.setText("");
            try {
                JSONArray liste = new JSONArray(reponse);
                for(int i = 0; i < liste.length(); i++)
                    CampDeJour.listeEnfants.add(new Enfant(new JSONObject(liste.getString(i))));

                Intent listeParent = new Intent(this, ListeParent.class);
                CampDeJour.motDePasse = motdepasse;
                startActivity(listeParent);


            }
            catch(Exception e) {}

        }

    }
  @Override
    public void onClick(View v) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }

}
