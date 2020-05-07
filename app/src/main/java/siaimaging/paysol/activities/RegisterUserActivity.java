package siaimaging.paysol.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import siaimaging.paysol.R;
import siaimaging.paysol.domain.User;
import siaimaging.paysol.utils.Cryptographer;
import siaimaging.paysol.utils.DataStorage;

public class RegisterUserActivity extends AppCompatActivity{

    EditText mFirstNameField;
    EditText mLastNameField;
    AutoCompleteTextView mEmailView;
    EditText mPassowdField;
    EditText mRePassowdField;
    Button mRegisterButton;
    private final String className = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(className, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mFirstNameField = (EditText) findViewById(R.id.register_fist_name);
        mLastNameField= (EditText) findViewById(R.id.register_last_name);
        mEmailView = (AutoCompleteTextView)findViewById(R.id.register_email);
        mPassowdField = (EditText) findViewById(R.id.register_password);
        mRePassowdField = (EditText) findViewById(R.id.register_password_again);
        mRegisterButton = (Button)findViewById(R.id.register_user_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
                if(verifyFormCorrectness()) {
                    saveData();
                    finish();
                }
            }
        });
    }
    private void saveData(){
        Log.i(className, "saveData() called");
        try {
            DataStorage storage = DataStorage.getInstance();
//            OutputStream personalDataFile = storage.createPrivateFile(DataStorage.PersonalDataStorageFile);
            User user = new User(mFirstNameField.getText().toString(),
                    mEmailView.getText().toString(),
                    Cryptographer.getHash(mPassowdField.getText().toString()));
            user.setLastName(mLastNameField.getText().toString());
            storage.createUser(user);
        }catch (Exception e){
            Log.i(className, "Exception : " + e.getStackTrace());
        }
    }
    private boolean verifyFormCorrectness() {
        Log.i(className, "verifyFormCorrectness()");
        mPassowdField.setError(null);
        mRePassowdField.setError(null);
        mEmailView.setError(null);
        View focusView = null;
        if(mPassowdField.getText().toString().isEmpty()){
            mPassowdField.setError(getString(R.string.error_field_empty));
            focusView = mPassowdField;
        }else if( mRePassowdField.getText().toString().isEmpty()){
            mRePassowdField.setError(getString(R.string.error_field_empty));
            focusView = mRePassowdField;
        }else if(!mPassowdField.getText().toString().equals(mRePassowdField.getText().toString())){
            Log.i(className, mPassowdField.getText().toString() + " != " + mRePassowdField.getText().toString());
            mPassowdField.setError(getString(R.string.error_password_fields_mismatch));
            focusView = mPassowdField;
        }else if(!mEmailView.getText().toString().contains("@")){
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
        }
        if(focusView != null) {
            focusView.requestFocus();
            return false;
        }else{
            return true;
        }
    }
}
