package com.example.joseph.mortgagecalculator30;
// Calculate a Monthly Payment for a mortgage loan
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle; //for saving state information
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // for EditText listener
import android.widget.EditText; // for bill amount imput
import android.widget.SeekBar; // for changing the tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeeBar listener
import android.widget.TextView; // for displaying text
import java.text.DecimalFormat; // format decimals
import java.text.NumberFormat; // for concurrency formatting
import java.lang.Math; // using mathematical properties such as exponents

public class MainActivity extends AppCompatActivity
{
    // currency and percent/decimal formatter objects
    private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
    private static final DecimalFormat df2 = new DecimalFormat("#.## '%'");

    private double price = 0.0; //loan amount entered by user
    private double down = 0.0; // down payment entered by user
    private double interest = 0.01; // interest rate entered by user, default at 1%
    private double year = 20.0; // initial year
    private TextView priceTextView; //shows formatted loan amount
    private TextView downTextView; // shows formatted down payment
    private TextView interestTextView;  // shows formatted interest rate
    private TextView yearTextView; // shows loan duration in years
    private TextView monthPayTextView; // shows formatted monthly payments


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to programmatically manipulated textView
        priceTextView = (TextView)findViewById(R.id.priceTextView);
        downTextView = (TextView)findViewById(R.id.downTextView);
        interestTextView = (TextView)findViewById(R.id.interestTextView);
        yearTextView = (TextView)findViewById(R.id.yearTextView);
        monthPayTextView = (TextView)findViewById(R.id.monthPayTextView);
        monthPayTextView.setText(numberFormat.format(0));

        // set priceEdit's textWatcher
        EditText priceEditText =  (EditText) findViewById(R.id.priceEditText);
        priceEditText.addTextChangedListener(priceEditTextWatcher);

        // set downEdit's textWatcher
        EditText downEditText = (EditText) findViewById((R.id.downEditText));
        downEditText.addTextChangedListener(downEditTextWatcher);

        // set interestEdit's textWatcher
        EditText interestEditText = (EditText) findViewById(R.id.interestEditText);
        interestEditText.addTextChangedListener(interestEditTextWatcher);

        // set yearSeeBar's OnSeekBarChangeListener
        SeekBar yearSeekBar = (SeekBar) findViewById(R.id.yearSeekBar);
        yearSeekBar.setOnSeekBarChangeListener(yearSeekBarListener);
    }

    private void calculate()
    {
        // calculate monthly payments
        double c = interest/12;
        double x = 1+ c;
        double exponent = year * 12;
        double power = Math.pow(x,exponent);
        double payment = ((price - down) *(c * power))/(power-1);

        // display monthly payments formatted as currency
        monthPayTextView.setText(numberFormat.format(payment));
    }

    // listener object for the SeekBar's progress change event
    private final OnSeekBarChangeListener yearSeekBarListener = new OnSeekBarChangeListener() {
        @Override
        // update year, then call calculate
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            year = progress;
            String s = Double.toString(year);
            yearTextView.setText(s);
            calculate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    };

    //listener object for the priceEditText's text-changed event
    private final TextWatcher priceEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        //called when the user modifies the loan amount
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

            try{ // get loan amount and display currency formatted value
                price = Double.parseDouble(s.toString())/100.0;
                priceTextView.setText(numberFormat.format(price));
            }catch(NumberFormatException e){ // if s is empty or non-numeric
                priceTextView.setText("");
                price = 0.0;
            }
            calculate(); // update the monthly payment
        }

        @Override
        public void afterTextChanged(Editable s) { }
    };

    //listener object for the downEditText's text-changed event
    private final TextWatcher downEditTextWatcher = new TextWatcher() {
        @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        //called when the user modifies the down payment amount
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            try{ // get down payment amount and display currency formatted value
                down = Double.parseDouble(s.toString())/100.0;
                downTextView.setText(numberFormat.format(down));
            }catch(NumberFormatException e){ // if s is empty or non-numeric
                downTextView.setText("");
                down = 0.0;
            }
            calculate(); // update the monthly payment

        }

        @Override
        public void afterTextChanged(Editable s) { }
    };

    //listener object for the interestEditText's text-changed event
    private final TextWatcher interestEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        //called when the user modifies the interest rate
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            try{ // get interest and display as percent formatted value
                interest = Double.parseDouble(s.toString())/10000.0;
                double fix = interest * 100;
                interestTextView.setText(df2.format(fix));
            }catch (NumberFormatException e){ // if s is empty or non-numeric
                interestTextView.setText("");
                interest = 0.01;
            }
            calculate(); // update the monthly payment

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
