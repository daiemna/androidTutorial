package siaimaging.paysol.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import siaimaging.paysol.R;

public class RegisterUserActivity extends AppCompatActivity {


    AutoCompleteTextView mEmailView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mEmailView = (AutoCompleteTextView)findViewById(R.id.register_email);
    }
}
