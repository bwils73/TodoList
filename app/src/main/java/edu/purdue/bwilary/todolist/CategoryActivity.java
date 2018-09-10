package edu.purdue.bwilary.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CategoryActivity";
    private EditText categoryEditText;
    private Button addBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //Initialize all views using findViewById()
        categoryEditText = (EditText) findViewById(R.id.content_edittext);
        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        addBtn = (Button) findViewById(R.id.add_btn);

        //Register OnClickListener for the two buttons
        cancelBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                onBackPressed();
                break;
            /*
                (1) If category is empty, popup a dialog says 'Category cannot be empty'
                (2) If category already exists, popup a dialog says 'Category exists'
                (3) If a valid category is input, add it to MainActivity.newCategories and go back to MainActivity using onBackPressed()
            */
            case R.id.add_btn:
                String category = categoryEditText.getText().toString();
                if (category.isEmpty()) {
                    //Code to show an AlertBox if the field is empty
                    AlertDialog alertDialog = new AlertDialog.Builder(CategoryActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Category field cannot be empty");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else if(MainActivity.categories.indexOf(category) > -1) {
                    AlertDialog alertDialog = new AlertDialog.Builder(CategoryActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Category already exists");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    MainActivity.newCategories.add(category);
                    onBackPressed();
                }
        }
    }
}
