package com.studycompanion.listapp;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

public class ImageCardFragment extends Fragment {
    Button CreateCard;

    RecyclerView recyclerView;
    private  ImageCardAdapter imageCardAdapter;
    private List<ImageCardModel> imageCardList = new ArrayList<>();
    private static final int PICK_IMAGE_REQUEST = 1;
    private DBHelper dbHelper;

    private Uri selectedImageUri;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_card_fragment, container, false);
        dbHelper = new DBHelper(getContext());
        CreateCard = view.findViewById(R.id.create_button_imagecards);
        recyclerView = view.findViewById(R.id.recyclerView_imagecard);
        imageCardAdapter = new ImageCardAdapter(imageCardList, getContext());


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(imageCardAdapter);
        imageCardAdapter.setOnImageDeleteClickListener(new ImageCardAdapter.OnImageDeleteClickListener() {
            @Override
            public void onImageDeleteClick(ImageCardModel imageCard, int position) {
                // Handle the delete action for image cards
                showDeleteImageCardDialog(imageCard, position);
            }
        });


        CreateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openGallery();

            }
        });
        imageCardAdapter.setOnImageDeleteClickListener(new ImageCardAdapter.OnImageDeleteClickListener() {
            @Override
            public void onImageDeleteClick(ImageCardModel imageCard, int position) {
                // Handle the delete action for image cards
                showDeleteImageCardDialog(imageCard, position);
            }
        });


        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Load data from the database every time the fragment is resumed
        imageCardAdapter.loadDataFromDatabase();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            // Create a new ImageCardModel object
            ImageCardModel imageCard = new ImageCardModel(selectedImageUri);

            // Add the new image card to the ImageCardAdapter
            imageCardAdapter.addImageCard(imageCard);

            // Add the new image card to the database
            imageCardAdapter.addCardToDatabase(imageCard);

            // Notify the adapter that the data set has changed
            imageCardAdapter.notifyDataSetChanged();
        }
    }


    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    private void showDeleteImageCardDialog(final ImageCardModel imageCard, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle("Delete Image Card");
        builder.setMessage("Are you sure you want to delete this image card?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Remove the image card from the RecyclerView adapter
                imageCardAdapter.removeCard(position);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing or add any additional logic if needed
            }
        });

        builder.show();
    }

}
