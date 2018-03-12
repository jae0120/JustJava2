/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        Boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        Boolean hasChocolate = chocolate.isChecked();
        EditText name = (EditText) findViewById(R.id.name_text_input);
        String orderName = name.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        composeEmail(orderName, createOrderSummary(price, hasWhippedCream, hasChocolate, orderName));
      //  displayMessage(createOrderSummary(price, hasWhippedCream, hasChocolate, orderName));
    }

    /**
     * This method creates the order Summary
     */

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String summary = getString(R.string.Name, name);
        summary += "\n" + getString(R.string.whipped, hasWhippedCream);
        summary += "\n" + getString(R.string.Chocolate, hasChocolate);
        summary += "\n" + getString(R.string.summary_quantity, quantity);
        summary += "\n" + getString(R.string.Total, NumberFormat.getCurrencyInstance().format(price));
        summary += "\n" + getString(R.string.thank_you);
        return summary;
    }

    // This method creates the email intent

    public void composeEmail(String name, String orderSummary) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
      //  intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean hasWhippedCream, boolean chocolate) {
        int price = 5;
        if(hasWhippedCream) {
            price++;
        }
        if (chocolate) {
            price++;
        }
        return quantity * price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quant) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quant);
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "That is too much coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You must order at least one coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given text on the screen.
     */
   /** private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }*/
}

