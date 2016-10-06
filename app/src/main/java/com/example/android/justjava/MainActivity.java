package com.example.android.justjava; /**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Global variables set here.
     */
    int quantity = 0;
    String name = "NoName";
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;

    /**
     * This method is called when the order button is clicked.
     */

//    public void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }


    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        }
    }

    public void decrement(View view) {
        if (quantity > 0) {
            quantity = quantity - 1;
            display(quantity);
        }
    }

    /**
     * Gets the name of the customer, and status of checkboxes, passes them into the order summary
     */
    public void submitOrder(View view) {
        EditText name_of_customer = (EditText) findViewById(R.id.name_of_customer);
        name = name_of_customer.getText().toString();
        Log.i("Name of customer", name);
        CheckBox whipped = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        hasWhippedCream = whipped.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.checkbox_chocolate);
        hasChocolate = chocolate.isChecked();
        int price = calculatePrice();
        createOrderSummary(price, name, hasWhippedCream, hasChocolate);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice() {
        int price = 5;
        if (hasWhippedCream) {
            price = price + 1;
        }
        if (hasChocolate) {
            price = price + 2;
        }
        return (quantity * price);

    }

    /**
     * returns a summary of the order
     *
     * @param priceOfOrder    is the price of the order, calculated earlier
     * @param hasWhippedCream is whether or not the customer wants whipped cream
     * @param hasChocolate    is whether or not the customer wants chocolate
     */
    private void createOrderSummary(int priceOfOrder, String name, boolean hasWhippedCream,
                                      boolean hasChocolate) {
        String returnString = getString(R.string.name) + " " + name;
        returnString = returnString + "\n" + getString(R.string.addWhipped) + " " + hasWhippedCream;
        returnString = returnString + "\n" + getString(R.string.addChocolate) + " " + hasChocolate;
        returnString = returnString + "\n" + getString(R.string.summaryQuantity) + " " + quantity;
        returnString = returnString + "\n" + getString(R.string.total) + " $" + priceOfOrder;
        returnString = returnString + "\n" + getString(R.string.thank_you);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "evlpacman@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.orderFor) + " " + name);
        intent.putExtra(Intent.EXTRA_TEXT, returnString);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
