package edu.purdue.bwilary.todolist;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * This Activity is launched if,
 *     (1) The round '+' button is clicked, meaning the user want to add a new todoitem
 *     (2) One row on the todolist is clicked, meaning the user want to edit an existing todoitem
 * So, we need to do different initialization based on which condition leads to this activity.
 * Also, the behavior of the saveBtn depends on the condition.
 */

public class ItemActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ItemActivity";
    private Spinner categorySelector;
    private EditText dateEditText;
    private EditText noteEditText;
    private ImageButton deleteBtn;
    private Button saveBtn;
    private Button cancelBtn;
    private TextView titleView;
    private Calendar myCalendar;
    private int passedItemIndex;
    private TodoItem todoItem;

    public static List<String> defaultCategories = new ArrayList<>(Arrays.asList("Life","Work"));
    private List<String> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        //If this activity is used to edit an existing TodoItem (a row on the todolist was clicked),
        //the index of that TodoItem in the showData list will be passed
        //Get the index from the intent if any, passedItemIndex will be -1 if no index is passed
        passedItemIndex = getIntent().getIntExtra(MainActivity.ITEM_KEY, -1);
        if(passedItemIndex!=-1)
            todoItem = MainActivity.showData.get(passedItemIndex);

        //Initialize all views
        titleView = (TextView)findViewById(R.id.title);
        categorySelector = (Spinner)findViewById(R.id.category_spinner);
        dateEditText = (EditText)findViewById(R.id.date_edittext);
        noteEditText = (EditText)findViewById(R.id.content_edittext);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);
        saveBtn = (Button)findViewById(R.id.save_btn);
        deleteBtn = (ImageButton)findViewById(R.id.delete_btn);


        //Set options for the spinner
        //We combine the defaultCategories and the user defined categories together
        categories = new ArrayList<>();
        categories.addAll(defaultCategories);
        categories.addAll(MainActivity.newCategories);
        categorySelector = (Spinner)findViewById(R.id.category_spinner);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(ItemActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                categories);
        categorySelector.setAdapter(spinnerArrayAdapter);

        //Register onClickListner for the two buttons
        cancelBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        //Set Datepicker for dateEdittext
        setDatepicker();

        if(passedItemIndex==-1){
            //If passedItemIndex==-1, we are using this activity to add a new item
            titleView.setText("Add New TODO Item");
        }else{
            //Else, we are using this activity to edit an existing item
            //Set the title of the activity
            titleView.setText("Edit TODO Item");
            //HINT: For EditText, use setText()
            //      For Spinner, use setSelection(). setSelection requires you to pass the index of the option you want to set.
            //      categories.indexOf(todoItem.getCategory()) gives you the index
            categorySelector.setSelection(categories.indexOf(todoItem.getCategory()));
            dateEditText.setText(MainActivity.allData.get(passedItemIndex).getDate());
            noteEditText.setText(MainActivity.allData.get(passedItemIndex).getNote());

            //Enable the deleteBtn
            deleteBtn.setVisibility(View.VISIBLE);
            deleteBtn.setClickable(true);

        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_btn:
                onBackPressed();
                break;
            case R.id.save_btn:
                String date = dateEditText.getText().toString();
                String note = noteEditText.getText().toString();
                String category = categorySelector.getSelectedItem().toString();

                if(date.isEmpty()||note.isEmpty()){
                    //Code to show an AlertBox if the field is empty
                    AlertDialog alertDialog = new AlertDialog.Builder(ItemActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Date and Note fields cannot be empty");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }else{
                    if(passedItemIndex==-1) {
                        //      So, create a new TodoItem Object and add it to MainActivity.allData
                        TodoItem item = new TodoItem(category, note, date);
                        MainActivity.allData.add(item);
                    } else{
                        //      So, modify the fields of todoItem with the new inputs using the setter methods of TodoItem
                        MainActivity.allData.get(passedItemIndex).setCategory(category);
                        MainActivity.allData.get(passedItemIndex).setDate(date);
                        MainActivity.allData.get(passedItemIndex).setNote(note);
                    }
                    onBackPressed();
                }
                break;
            case R.id.delete_btn:
                //       (1) The AlertDialog should have two buttons, 'No' and 'Yes'
                //       (2) When 'No' is clicked, dismiss the dialog
                //       (3) When 'Yes' is clicked, remove the todoItem from MainActivity.allData.
                //           Then, dismiss the dialog and call onBackPressed() to go back to MainActivity
                //HINT: (1) To add two buttons in AlertDialog, call alertDialog.setButton() twice
                //      (2) To remove item from an ArrayList, use the following code
                //          MainActivity.allData.remove(MainActivity.allData.indexOf(todoItem));
                //Code to show an AlertBox if the field is empty
                AlertDialog alertDialog = new AlertDialog.Builder(ItemActivity.this).create();
                alertDialog.setTitle("Delete Todo Item");
                alertDialog.setMessage("Would you like to delete this item?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.allData.remove(MainActivity.allData.indexOf(todoItem));
                                dialog.dismiss();
                                onBackPressed();
                            }
                        });
                alertDialog.show();
                break;
        }
    }
    /******************************* DON'T TOUCH CODE BELOW *****************************/
    private void setDatepicker() {
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ItemActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateEditText.setText(sdf.format(myCalendar.getTime()));
    }
}
