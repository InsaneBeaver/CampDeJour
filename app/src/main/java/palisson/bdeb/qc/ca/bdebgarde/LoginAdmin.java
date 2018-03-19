package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.view.Gravity;
import android.app.*;
import android.content.DialogInterface;

public class LoginAdmin extends AppCompatActivity  {

    public final static String MESSAGE_MDP_ADMIN = "MDPADMIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        ((EditText)findViewById(R.id.passwordTextAdmin)).setGravity(Gravity.CENTER_HORIZONTAL);
    }


    private boolean checkPassword(){
        EditText passwordConfirmer = (EditText) findViewById(R.id.passwordTextAdmin);
        String mdp = passwordConfirmer.getText().toString();
        return CampDeJour.estBonMotDePasse(mdp);
    }


    public void finishLogin(View v){
        Intent listeAdmin = new Intent(this, ListeAdmin.class);

        EditText passwordConfirmer = (EditText) findViewById(R.id.passwordTextAdmin);


        if(checkPassword()) {

            listeAdmin.putExtra(MESSAGE_MDP_ADMIN, passwordConfirmer.getText().toString());
            passwordConfirmer.setText("");
          //  startActivity(listeAdmin);
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(LoginAdmin.this).create();
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
