package com.example.networks.marshmallowapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {
    private EditText op1;
    private EditText op2;
    private Button plus;
    private Button minus;
    private Button divide;
    private Button multi;
    private Button clear;
    private TextView txtResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        op1 = (EditText) findViewById(R.id.operand1);
        op2 = (EditText) findViewById(R.id.operand2);



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        plus = (Button) findViewById(R.id.pBtn);
        minus = (Button) findViewById(R.id.mBtn);
        divide = (Button) findViewById(R.id.divBtn);
        multi = (Button) findViewById(R.id.multiBtn);
        clear = (Button) findViewById(R.id.btnClear);
        txtResult = (TextView) findViewById(R.id.txtResult);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(op1.getText().length() > 0 && op2.getText().length() > 0){
                        //get values and calculate


                        final double oper1 = Double.parseDouble(op1.getText().toString());
                        final double oper2 = Double.parseDouble(op2.getText().toString());
                        double result = oper1+oper2;
                        txtResult.setText(Double.toString(result));

                    }else{

                        Toast.makeText(CalculatorActivity.this,"Enter valid numbers",Toast.LENGTH_LONG).show();
                    }

                }
            });

            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    op1.setText("");
                    op2.setText("");
                    txtResult.setText("0.0");
                    op1.requestFocus();
                }
            });



    }

}
