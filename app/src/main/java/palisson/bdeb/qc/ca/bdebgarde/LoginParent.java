package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginParent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_parent);
    }


    private boolean checkPassword(){
        EditText passwordConfirmer = (EditText) findViewById(R.id.passwordText);
        if(passwordConfirmer.getText().toString().equals("")||passwordConfirmer.getText().toString().equals(null))
            return false;
        else
            return true;
    }

    public void finishLogin(View v){
        Intent listeParent = new Intent(this, ListeParent.class);

        EditText passwordConfirmer = (EditText) findViewById(R.id.passwordText);


        if(checkPassword()) {
            passwordConfirmer.setText("");
            startActivity(listeParent);
        }
    }
}
