package siaimaging.paysol.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import siaimaging.paysol.R;

public class PaymentActivity extends AppCompatActivity {

    private final String className = this.getClass().getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(className, "Constructor called for payment activity.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // provide items of the drop down list
        Spinner spinner = (Spinner) findViewById(R.id.payment_currency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                        R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(className, "In onItemSelected");
                Log.i(className, "Position:" + position);
                Log.i(className, "ID:" + id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(className, "In onNothingSelected");
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void onSpinnerClick(){
        Log.i(className, "Response to spinner click. OK.");
    }

}
