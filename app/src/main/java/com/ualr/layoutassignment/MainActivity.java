package com.ualr.layoutassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    // TODO 02. Create a new method called "calculateTotal" for calculating the invoice's total amount of money

    // TODO 03. Bind the "calculateTotal" method to the button with the "CALCULATE TOTAL" label

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton discountBtn = (ToggleButton) findViewById(R.id.discount_togglebtn);
        ToggleButton noDiscountBtn = (ToggleButton) findViewById(R.id.noDiscount_togglebtn);

        discountBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    discountBtn.setChecked(true);
                    discountBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    discountBtn.setTextColor(getResources().getColor(R.color.grey_3));
                    noDiscountBtn.setBackgroundColor(getResources().getColor(R.color.grey_3));
                    noDiscountBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
                    noDiscountBtn.setChecked(false);
                }

            }
        });

        noDiscountBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    noDiscountBtn.setChecked(true);
                    noDiscountBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    noDiscountBtn.setTextColor(getResources().getColor(R.color.grey_3));
                    discountBtn.setBackgroundColor(getResources().getColor(R.color.grey_3));
                    discountBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
                    discountBtn.setChecked(false);
                }
            }
        });
    }

    public void calculateTotal(View view){
        CheckBox product1CB = (CheckBox) findViewById(R.id.product1_cb);
        CheckBox product2CB = (CheckBox) findViewById(R.id.product2_cb);
        CheckBox product3CB = (CheckBox) findViewById(R.id.product3_cb);
        CheckBox product4CB = (CheckBox) findViewById(R.id.product4_cb);

        ToggleButton discount = (ToggleButton) findViewById(R.id.discount_togglebtn);

        boolean p1Checked = product1CB.isChecked();
        boolean p2Checked = product2CB.isChecked();
        boolean p3Checked = product3CB.isChecked();
        boolean p4Checked = product4CB.isChecked();

        boolean discountChecked = discount.isChecked();

        EditText p1Value = (EditText) findViewById(R.id.product1Value);
        EditText p2Value = (EditText) findViewById(R.id.product2Value);
        EditText p3Value = (EditText) findViewById(R.id.product3Value);
        EditText p4Value = (EditText) findViewById(R.id.product4Value);

        double p1Val = 0.00, p2Val = 0.00, p3Val = 0.00, p4Val = 0.00, total;

        if(p1Checked){
            p1Val = Double.parseDouble(p1Value.getText().toString());
        }
        if(p2Checked){
            p2Val = Double.parseDouble(p2Value.getText().toString());
        }
        if(p3Checked){
            p3Val = Double.parseDouble(p3Value.getText().toString());
        }
        if(p4Checked){
            p4Val = Double.parseDouble(p4Value.getText().toString());
        }

        if(discountChecked){
            total = (p1Val + p2Val + p3Val + p4Val) * 0.75;
        }else{
            total = p1Val + p2Val + p3Val + p4Val;
        }

        String totalStr = String.format("%.2f", total);

        TextView _total = (TextView) findViewById(R.id.total_dollars);

        _total.setText(totalStr);

    }
}