package e.maxtauro.onroute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Context context;
    Button btnEmailLogin;
    Button btnFbookLogin;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build());

    private final static int LOGIN_PERMISSION=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            Intent intent = new Intent(MainActivity.this, ListFriends.class);
            startActivity(intent);
            finish();

        }
        else {

            this.context = this;

            btnEmailLogin = (Button) findViewById(R.id.email_login_button);
            btnEmailLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "clicked email", Toast.LENGTH_SHORT).show();

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAllowNewEmailAccounts(true)
                                    .build(), LOGIN_PERMISSION);
                }
            });

            btnFbookLogin = (Button) findViewById(R.id.fb_login_button);
            btnFbookLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "clicked fbook", Toast.LENGTH_SHORT).show();

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setProviders(providers)
                                    .build(), LOGIN_PERMISSION);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LOGIN_PERMISSION){
            startNewActivity(resultCode,data);
        }
    }

    private void startNewActivity(int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            Intent intent = new Intent(MainActivity.this, ListFriends.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, "Invalid Signin", Toast.LENGTH_SHORT).show();
        }
    }
}
