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


public class LoginParent extends AppCompatActivity  implements View.OnClickListener  {

    private IntentIntegrator qrScan;
    public final static String MESSAGE_MDP_PARENT = "MDPPARENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_parent);
        ((EditText)findViewById(R.id.passwordText)).setGravity(Gravity.CENTER_HORIZONTAL);
        qrScan = new IntentIntegrator(this);

    }


    private boolean checkPassword(){
        EditText passwordConfirmer = (EditText) findViewById(R.id.passwordText);
        String mdp = passwordConfirmer.getText().toString();
        return CampDeJour.estBonMotDePasse(mdp);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //si pas information
            if (result.getContents() == null) {
                Toast.makeText(this, "Résultat pas Trouvé", Toast.LENGTH_LONG).show();
            } else {
                //si information
                if (result.getContents().equals("Hello")) {
                    Intent listeParent = new Intent(this, ListeParent.class);
                    listeParent.putExtra(MESSAGE_MDP_PARENT, "2");

                    startActivity(listeParent);
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void finishLogin(View v){
        Intent listeParent = new Intent(this, ListeParent.class);

        EditText passwordConfirmer = (EditText) findViewById(R.id.passwordText);


        if(checkPassword()) {

            listeParent.putExtra(MESSAGE_MDP_PARENT, passwordConfirmer.getText().toString());
            passwordConfirmer.setText("");
            startActivity(listeParent);
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(LoginParent.this).create();
            alertDialog.setTitle("Alerte");
            alertDialog.setMessage("Mot de passe inconnu");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }    @Override
    public void onClick(View v) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }

}
