package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.Gravity;
import android.app.*;
import android.content.DialogInterface;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


public class LoginParent extends AppCompatActivity  implements View.OnClickListener  {

    private IntentIntegrator qrScan;
    public final static String MESSAGE_MDP_PARENT = "MDPPARENT";
    EditText passwordConfirmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.passwordConfirmer = (EditText) findViewById(R.id.passwordText);
    //    Toast.makeText(this, "TEXTE " + this.passwordConfirmer, Toast.LENGTH_LONG).show();
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
        ouvrirListe(CampDeJour.getHash(passwordConfirmer.getText().toString()));
    }

    private void ouvrirListe(String hash)
    {
        if(!CampDeJour.hashEstConnu(hash))
        {
            AlertDialog alertDialog = new AlertDialog.Builder(LoginParent.this).create();
            alertDialog.setTitle("Alerte");
            alertDialog.setMessage("Erreur d'identification");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        else
        {
            passwordConfirmer.setText("");
            Intent listeParent = new Intent(this, ListeParent.class);
            listeParent.putExtra(MESSAGE_MDP_PARENT, hash);
            startActivity(listeParent);

        }

    }
  @Override
    public void onClick(View v) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }

}
