package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Intent serviceIntent = new Intent(this, ServiceClient.class);

        try {startService(serviceIntent);}
        catch(Exception e) {Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show(); }

    }


    public void loginParent(View v){
        Intent loginParent = new Intent(this, LoginParent.class);
        startActivity(loginParent);
    }

    public void loginAdmin(View v){
        Intent loginAdmin = new Intent(this, LoginAdmin.class);
        startActivity(loginAdmin);
    }
}
