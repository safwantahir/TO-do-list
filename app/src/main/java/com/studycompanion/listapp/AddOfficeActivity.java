package com.studycompanion.listapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddOfficeActivity extends AppCompatActivity {
    private EditText cardNameEditText, cardNumberEditText, userNameEditText, validThruEditText, cvvCodeEditText;
    private Button submitButton;
    private Calendar calendar;
    private static final int REQUEST_CODE_ADD_CARD = 1;

    private SimpleDateFormat dateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_card);



        cardNameEditText = findViewById(R.id.EdittextCardName);
        cardNumberEditText = findViewById(R.id.EdittextCardNumber);
        userNameEditText = findViewById(R.id.editTextUserName);
        validThruEditText = findViewById(R.id.EdittextValidThruDate);
        cvvCodeEditText = findViewById(R.id.EdittextCVVCode);
        submitButton = findViewById(R.id.submitButton);

        // Initialize the date format
        dateFormat = new SimpleDateFormat("MM/yyyy", Locale.US);

        // Initialize the Calendar object
        calendar = Calendar.getInstance();


        validThruEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        cvvCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Ensure the CVV code doesn't exceed 4 digits
                if (s.length() > 4) {
                    cvvCodeEditText.setText(s.subSequence(0, 4));
                    cvvCodeEditText.setSelection(4);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Start the AddCardActivity to add card details
                // Get the entered card details
                String cardName = cardNameEditText.getText().toString();
                String cardNumberWithSpaces = cardNumberEditText.getText().toString(); // Get card number with spaces
                // Remove spaces before storing the formatted card number
                String cardNumber = cardNumberWithSpaces.replaceAll("\\s+", "");
                String userName = userNameEditText.getText().toString();
                String validThru = validThruEditText.getText().toString();
                String cvvCode = cvvCodeEditText.getText().toString();


                // Create a CardModel object
                OfficeModel newCard = new OfficeModel(cardName, cardNumberWithSpaces, userName, validThru, cvvCode);

                // Pass the newCard back to the calling fragment
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newCard", newCard);
                setResult(RESULT_OK, resultIntent);
                finish();

            }

        });




    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the validThruEditText with the selected date
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        validThruEditText.setText(dateFormat.format(calendar.getTime()));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Set the date picker to show only the month and year
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.getDatePicker().setCalendarViewShown(false);

        datePickerDialog.show();
    }
}
