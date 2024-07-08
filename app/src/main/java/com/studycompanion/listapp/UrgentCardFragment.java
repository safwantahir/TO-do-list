package com.studycompanion.listapp;

import static android.widget.Toast.makeText;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UrgentCardFragment extends Fragment {

    private RecyclerView urgentrecyclerView;
    private UrgentCardFragment.UrgentAdapter urgentcardsAdapter;
    private List<UrgentModel> urgentcardList;
    private Button urgentaddButton;

    private Calendar urgentcalendar;
    private EditText utgenteditValidThru;
    private SimpleDateFormat urgentdateFormat;
    private UrgentDataBaseHelper dbHelper;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1; // You can use any integer value


    private static final int REQUEST_CODE_ADD_CARD = 1;

    public UrgentCardFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.urgent_card_fragment, container, false);

        utgenteditValidThru = view.findViewById(R.id.EdittextValidThruDate);
        urgentcalendar = Calendar.getInstance();
        urgentdateFormat = new SimpleDateFormat("MM/yyyy", Locale.US);
        // Initialize RecyclerView and other UI components
        urgentrecyclerView = view.findViewById(R.id.recyclerView_textcard);
        urgentaddButton = view.findViewById(R.id.create_button_textCard);
        urgentdateFormat = new SimpleDateFormat("MM/yyyy", Locale.US);


        // Initialize the cardList and adapter
        urgentcardList = new ArrayList<>();
        urgentcardsAdapter = new UrgentAdapter(getActivity(), urgentcardList);

        // Set RecyclerView layout manager and adapter
        urgentrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        urgentrecyclerView.setAdapter(urgentcardsAdapter);
        urgentloadCardsFromDatabase();

        // Handle click on the "Add" button
        urgentaddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddUrgentActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_CARD);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_CARD && resultCode == getActivity().RESULT_OK) {
            // Retrieve the newCard object from the AddCardActivity
            UrgentModel urgentnewCard = data.getParcelableExtra("newCard");

            // Add the newCard to your cardList and notify the adapter
            urgentcardList.add(urgentnewCard);
            urgentcardsAdapter.notifyDataSetChanged();
            UrgentinsertCardIntoDatabase(urgentnewCard);
        }
    }

    // Adapter for your RecyclerView
    private class UrgentAdapter extends RecyclerView.Adapter<UrgentCardFragment.UrgentAdapter.ViewHolder> {

        private List<UrgentModel> cardList;
        LinearLayout mainlayout;

        public UrgentAdapter(Context context, List<UrgentModel> cardList) {
            this.cardList = cardList;
        }

        @Override
        public UrgentCardFragment.UrgentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Inflate the card item layout
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.urgent_card_layout, parent, false);
            return new UrgentCardFragment.UrgentAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final UrgentCardFragment.UrgentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            final UrgentModel card = cardList.get(position);


            // Set other views in your card here

            int backgroundResId = card.getUrgentbackgroundResId();
            if (backgroundResId != 0) {
                Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundResId);
                holder.mainlayout.setBackground(drawable);
            } else {
                // Handle the case where no background is set for the card
                holder.mainlayout.setBackgroundResource(R.drawable.card1); // Set a default background
            }

            // Set card details in the UI elements
            holder.cardName.setText(card.geturgentCardName());

            // Format the card number with spaces and display it
            holder.cardNum.setText(card.getUrgentcardNumber());

            holder.username.setText(card.getUrgentuserName());
            holder.valid.setText(card.getUrgentvalidThru());
            holder.cvvCode.setText(card.getUrgentcvvCode());

            // Check the isBlurred state and update the text views accordingly
            if (card.isBlurred()) {
                // Blur the text views (card_num, validThru, cvvCode)
                holder.cardNum.setTextColor(getResources().getColor(android.R.color.transparent));
                holder.valid.setTextColor(getResources().getColor(android.R.color.transparent));
                holder.cvvCode.setTextColor(getResources().getColor(android.R.color.transparent));
            } else {
                // Unblur the text views (card_num, validThru, cvvCode)
                holder.cardNum.setTextColor(getResources().getColor(R.color.white));
                holder.valid.setTextColor(getResources().getColor(R.color.white));
                holder.cvvCode.setTextColor(getResources().getColor(R.color.white));
            }


            // Handle click on eye image to toggle blur state
            holder.eyeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toggle the blur state
                    card.setBlurred(!card.isBlurred());
                    notifyDataSetChanged();
                }
            });

            // Handle click on edit image to show image options dialog
            holder.editimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImageOptionsDialog(position); // Pass the position to the dialog
                }
            });

            // Handle click on dot image to show edit card details dialog
            holder.dotimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showEditCardDialog(position); // Show the "Edit Card Details" dialog
                }
            });
            // Inside onBindViewHolder method of CardsAdapter
            holder.delteimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Show a confirmation dialog before deleting the card
                    showDeleteConfirmationDialog(position);
                }
            });

            holder.downloadimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if the WRITE_EXTERNAL_STORAGE permission is granted
                    if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // Permission is granted, capture and save the screenshot
                        captureAndSaveScreenshot(holder.mainlayout);
                    } else {
                        // Permission is not granted, request it from the user
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return cardList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView cardNum, valid, cvvCode, cardName, username;
            ImageView eyeImage, editimage, dotimage, downloadimage, delteimage;
            LinearLayout mainlayout;

            public ViewHolder(View itemView) {
                super(itemView);
                cardName = itemView.findViewById(R.id.card_name);

                cardNum = itemView.findViewById(R.id.card_num);
                username = itemView.findViewById(R.id.user_name);
                valid = itemView.findViewById(R.id.valid);
                cvvCode = itemView.findViewById(R.id.cvvcode);
                eyeImage = itemView.findViewById(R.id.eye);
                editimage = itemView.findViewById(R.id.dot);
                dotimage = itemView.findViewById(R.id.edit);
                mainlayout = itemView.findViewById(R.id.main_layout);
                downloadimage = itemView.findViewById(R.id.download);
                delteimage = itemView.findViewById(R.id.delete);

            }

            public void setMainLayoutBackground(Drawable drawable) {
                mainlayout.setBackground(drawable);
            }

        }

    }


    private void showImageOptionsDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select an Image");
        final String[] imageOptions = {"Purple", "Light Blue", "Red", "Blue", "Golden", "Green", "Pink"};
        builder.setItems(imageOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle image selection here
                switch (which) {
                    case 0:
                        setBackgroundForItem(position, R.drawable.card1);
                        break;
                    case 1:
                        setBackgroundForItem(position, R.drawable.card2);
                        break;
                    case 2:
                        setBackgroundForItem(position, R.drawable.card3);
                        break;
                    case 3:
                        setBackgroundForItem(position, R.drawable.card4);
                        break;
                    case 4:
                        setBackgroundForItem(position, R.drawable.card5);
                        break;
                    case 5:
                        setBackgroundForItem(position, R.drawable.card6);
                        break;
                    case 6:
                        setBackgroundForItem(position, R.drawable.card7);
                        break;

                }
            }
        });
        builder.show();
    }

    private void setBackgroundForItem(int position, int drawableId) {
        UrgentModel urgentcard = urgentcardList.get(position);
        urgentcard.setUrgentbackgroundResId(drawableId);
        urgentupdateCardInDatabase(urgentcard);
        urgentcardsAdapter.notifyItemChanged(position);
    }

    private void showEditCardDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        // Inflate the custom layout for the dialog
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.edit_card_details, null);
        builder.setView(dialogView);

        // Find the EditText fields in the custom layout
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editCardName = dialogView.findViewById(R.id.EdittextCardName);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editCardNumber = dialogView.findViewById(R.id.EdittextCardNumber);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editUserName = dialogView.findViewById(R.id.editTextUserName);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editValidThru = dialogView.findViewById(R.id.EdittextValidThruDate);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editCvvCode = dialogView.findViewById(R.id.EdittextCVVCode);

        // Set filters to restrict input length
        //   editCardNumber.setFilters(new InputFilter[] { new InputFilter.LengthFilter(19)}); // Restrict to 16 digits

        // Add a TextWatcher to format the card number with spaces after every 4 digits

        // Get the card data at the specified position
        UrgentModel card = urgentcardList.get(position);

        // Set the current card data into the EditText fields
        editCardName.setText(card.geturgentCardName());

        // Format the card number with spaces and display it
        editCardNumber.setText(card.getUrgentcardNumber());
        editUserName.setText(card.getUrgentuserName());
        editValidThru.setText(card.getUrgentvalidThru());
        editCvvCode.setText(card.getUrgentcvvCode());

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the edited card data from the EditText fields
                String editedCardName = editCardName.getText().toString();
                String editedCardNumber = editCardNumber.getText().toString();
                String editedUserName = editUserName.getText().toString();
                String editedValidThru = editValidThru.getText().toString();
                String editedCvvCode = editCvvCode.getText().toString();
                editValidThru.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDatePickerDialog();
                    }
                });

                    // Update the card data at the specified position
                    UrgentModel editedCard = urgentcardList.get(position);
                    editedCard.urgentsetCardName(editedCardName);
                    editedCard.setUrgentcardNumber(editedCardNumber);
                    editedCard.setUrgentuserName(editedUserName);
                    editedCard.setUrgentvalidThru(editedValidThru);
                    editedCard.setUrgentcvvCode(editedCvvCode);

                    // Notify the adapter of the data change
                    urgentcardsAdapter.notifyDataSetChanged();
                    urgentupdateCardInDatabase(editedCard);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.length() == 16; // Check if card number has 16 digits
    }

    private boolean isValidCvvCode(String cvvCode) {
        return cvvCode.length() == 4; // Check if CVV code has 4 digits
    }

    private String formatCardNumberWithSpaces(String cardNumber) {
        StringBuilder formattedCardNumber = new StringBuilder();

        // Remove existing spaces if any
        cardNumber = cardNumber.replaceAll("\\s+", "");
        StringBuilder formattedStringBuilder = new StringBuilder();
        // Add a space after every 4 digits
        for (int i = 0; i < cardNumber.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                formattedStringBuilder.append(' ');
            }
            formattedCardNumber.append(cardNumber.charAt(i));
            if ((i + 1) % 4 == 0 && (i + 1) < cardNumber.length()) {
                formattedCardNumber.append(' ');
            }
        }

        return formattedCardNumber.toString();
    }

    private void captureAndSaveScreenshot(View view) {
        // Create a Bitmap of the specified view
        Bitmap screenshotBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(screenshotBitmap);
        view.draw(canvas);

        // Generate a unique file name using timestamp
        long timestamp = System.currentTimeMillis();
        String fileName = "card_screenshot_" + timestamp + ".png";

        // Define the directory where you want to save the screenshots
        File galleryFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        // Create the image file with the unique file name in the specified directory
        File imageFile = new File(galleryFolder, fileName);

        try {
            // Create an output stream to write the screenshot Bitmap to the file
            FileOutputStream fos = new FileOutputStream(imageFile);

            // Compress and save the Bitmap as a PNG image with maximum quality (100)
            screenshotBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            // Flush and close the output stream
            fos.flush();
            fos.close();

            // Show a toast message indicating successful saving
            makeText(requireContext(), "Screenshot saved to gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();

            // Show a toast message in case of failure to save the screenshot
            makeText(requireContext(), "Failed to save screenshot", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle permission request results
    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, capture and save the screenshot of a specific card's view
                // You can choose the card you want to capture by its position in the RecyclerView
                int cardPosition = 0;
                if (cardPosition >= 0 && cardPosition < urgentcardList.size()) {
                    // Capture and save the screenshot of the card's view
                    UrgentCardFragment.UrgentAdapter.ViewHolder viewHolder = (UrgentCardFragment.UrgentAdapter.ViewHolder) urgentrecyclerView.findViewHolderForAdapterPosition(cardPosition);
                    if (viewHolder != null) {
                        captureAndSaveScreenshot(viewHolder.mainlayout);
                    } else {
                        makeText(requireContext(), "Unable to capture screenshot of the selected card.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    makeText(requireContext(), "Invalid card position.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Permission denied, show a message to the user
                makeText(requireContext(), "Permission denied. Cannot save screenshot.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void urgentloadCardsFromDatabase() {
        UrgentDataSource dataSource = new UrgentDataSource(requireContext());
        dataSource.urgenteopen();
        List<UrgentModel> savedCards = dataSource.getAllurgentCards();
        dataSource.urgentclose();

        // Clear the existing cardList and add the retrieved cards
        urgentcardList.clear();
        urgentcardList.addAll(savedCards);

        // Notify the adapter of the data change
        urgentcardsAdapter.notifyDataSetChanged();
    }

    private void UrgentinsertCardIntoDatabase(UrgentModel urgentcard) {
        UrgentDataSource urgentdataSource = new UrgentDataSource(requireContext());
        urgentdataSource.urgenteopen();
        long cardId = urgentdataSource.urgentinsertCard(urgentcard);
        urgentdataSource.urgentclose();
    }

    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this card?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete the card from the list and database
                urgentdeleteCard(position);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void urgentdeleteCardFromDatabase(UrgentModel card) {
        UrgentDataSource dataSource = new UrgentDataSource(requireContext());
        dataSource.urgenteopen();
        dataSource.urgentdeleteCard(card);
        dataSource.urgentclose();
    }

    private void urgentdeleteCard(int position) {

        // Get the card to delete
        UrgentModel cardToDelete = urgentcardList.get(position);

        // Delete the card from the database first
        urgentdeleteCardFromDatabase(cardToDelete);
// Remove the card from the list
        urgentcardList.remove(position);

        urgentcardsAdapter.notifyItemRemoved(position);
        urgentcardsAdapter.notifyItemRangeChanged(position, urgentcardList.size());


    }


    public void urgentupdateCardInDatabase(UrgentModel card) {
        UrgentDataBaseHelper dbHelper = new UrgentDataBaseHelper(getContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues urgentvalues = new ContentValues();
        urgentvalues.put(UrgentDataBaseHelper.URGENT_COLUMN_BACKGROUND_RES_ID, card.getUrgentbackgroundResId());
        urgentvalues.put(UrgentDataBaseHelper.URGENT_COLUMN_CARD_NAME, card.geturgentCardName());
        urgentvalues.put(UrgentDataBaseHelper.URGENT_COLUMN_CARD_NUMBER, card.getUrgentcardNumber());
        urgentvalues.put(UrgentDataBaseHelper.URGENT_COLUMN_USER_NAME, card.getUrgentuserName());
        urgentvalues.put(UrgentDataBaseHelper.URGENTCOLUMN_VALID_THRU, card.getUrgentvalidThru());
        urgentvalues.put(UrgentDataBaseHelper.URGENT_COLUMN_CVV_CODE, card.getUrgentcvvCode());

        String whereClause = UrgentDataBaseHelper.URGENT_COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(card.urgentgetId())};

        database.update(UrgentDataBaseHelper.URGENT_TABLE_CARDS, urgentvalues, whereClause, whereArgs);
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(), // Use requireContext() to get the appropriate Context
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the validThru EditText with the selected date
                        urgentcalendar.set(Calendar.YEAR, year);
                        urgentcalendar.set(Calendar.MONTH, month);
                        utgenteditValidThru.setText(urgentdateFormat.format(urgentcalendar.getTime()));
                    }
                },
                urgentcalendar.get(Calendar.YEAR),
                urgentcalendar.get(Calendar.MONTH),
                urgentcalendar.get(Calendar.DAY_OF_MONTH)
        );

        // Set the date picker to show only the month and year
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.getDatePicker().setCalendarViewShown(false);

        datePickerDialog.show();
    }

}

