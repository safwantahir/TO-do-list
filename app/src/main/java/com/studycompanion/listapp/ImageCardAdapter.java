// ImageCardAdapter.java
package com.studycompanion.listapp;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageCardAdapter extends RecyclerView.Adapter<ImageCardAdapter.ImageCardViewHolder> {

    private List<ImageCardModel> imageCardList;

    private Context context;
    private DBHelper dbHelper;

    private OnImageDeleteClickListener onImageDeleteClickListener;
    public interface OnImageDeleteClickListener {
        void onImageDeleteClick(ImageCardModel imageCard, int position);
    }
    public ImageCardAdapter(List<ImageCardModel> imageCardList, Context context) {
        this.imageCardList = imageCardList;
        this.context = context;
        dbHelper = new DBHelper(context);

    }
    public void loadDataFromDatabase() {
        imageCardList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ImageCardModel imageCard = ImageCardModel.fromCursor(cursor);
                imageCardList.add(imageCard);
            } while (cursor.moveToNext());

            cursor.close();
            notifyDataSetChanged();
        }

        db.close();
    }
    public void setOnImageDeleteClickListener(OnImageDeleteClickListener listener) {
        this.onImageDeleteClickListener = listener;
    }
    @NonNull
    @Override
    public ImageCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card_layout, parent, false);
        return new ImageCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageCardViewHolder holder, int position) {
        ImageCardModel imageCard = imageCardList.get(position);

        // Load the image using Glide or your preferred image loading library
        Glide.with(context)
                .load(imageCard.getCardImageUri())
                .into(holder.ivCardImage);

        // Set click listener for image card
        holder.ivCardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click event for image card
                Toast.makeText(context, "Image Card Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        holder.deleteimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display the dialog through the listener
                if (onImageDeleteClickListener != null) {
                    int adapterPosition = holder.getAdapterPosition();
                    onImageDeleteClickListener.onImageDeleteClick(imageCard, adapterPosition);
                    removeCardFromDatabase(imageCard); // Add this line to remove the card from the database
                }
            }
        });


        // Add any other functionalities or UI updates as needed
    }
    private void removeCardFromDatabase(ImageCardModel imageCard) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_IMAGE_URI + "=?",
                new String[]{imageCard.getCardImageUri().toString()});
        db.close();
    }

    @Override
    public int getItemCount() {
        return imageCardList.size();
    }

    public void addImageCard(ImageCardModel imageCard) {
        imageCardList.add(imageCard);
        notifyItemInserted(imageCardList.size() - 1);
    }

    public class ImageCardViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCardImage;
        ImageView deleteimage;

        public ImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCardImage = itemView.findViewById(R.id.image);
            deleteimage = itemView.findViewById(R.id.delete);
        }
    }

    public void removeCard(int position) {
        // Get the card to delete
        ImageCardModel cardToDelete = imageCardList.get(position);

        // Remove the card from the list

        imageCardList.remove(position);
        // Call notifyItemRemoved and notifyItemRangeChanged on the current instance (this)
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, imageCardList.size());


    }
    public void addCardToDatabase(ImageCardModel imageCard) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = imageCard.toContentValues();
        db.insert(DBHelper.TABLE_NAME, null, values);
        db.close();
    }


}
