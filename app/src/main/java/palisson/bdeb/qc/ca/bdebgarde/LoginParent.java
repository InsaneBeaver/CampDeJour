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


public class LoginParent extends AppCompatActivity {

    public final static String MESSAGE_MDP_PARENT = "MDPPARENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_parent);
        ((EditText)findViewById(R.id.passwordText)).setGravity(Gravity.CENTER_HORIZONTAL);
    }


    private boolean checkPassword(){
        EditText passwordConfirmer = (EditText) findViewById(R.id.passwordText);
        String mdp = passwordConfirmer.getText().toString();
        return CampDeJour.estBonMotDePasse(mdp);
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
    }
}
