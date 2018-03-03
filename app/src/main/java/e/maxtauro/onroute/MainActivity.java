package e.maxtauro.onroute;

import android.content.Context;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Context context;
    Button btnEmailLogin;
    Button btnFbookLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        btnEmailLogin = (Button) findViewById(R.id.email_login_button);
        btnEmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked email", Toast.LENGTH_SHORT).show();
            }
        });

        btnFbookLogin = (Button) findViewById(R.id.fb_login_button);
        btnFbookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked fbook", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
