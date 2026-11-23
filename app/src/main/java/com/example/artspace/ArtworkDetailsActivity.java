package com.example.artspace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ArtworkDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "extra_artwork_title";
    public static final String EXTRA_ARTIST = "extra_artwork_artist";
    public static final String EXTRA_YEAR = "extra_artwork_year";
    public static final String EXTRA_WEBSITE_URL = "extra_artwork_website_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_details);

        TextView detailsTextView = findViewById(R.id.tv_details_info);
        Button visitWebsiteButton = findViewById(R.id.btn_visit_website);
        Button backButton = findViewById(R.id.btn_back_main);

        Intent incomingIntent = getIntent();
        String title = (incomingIntent != null && incomingIntent.hasExtra(EXTRA_TITLE))
                ? incomingIntent.getStringExtra(EXTRA_TITLE)
                : "Unknown Title";
        String artist = (incomingIntent != null && incomingIntent.hasExtra(EXTRA_ARTIST))
                ? incomingIntent.getStringExtra(EXTRA_ARTIST)
                : "Unknown Artist";
        String year = (incomingIntent != null && incomingIntent.hasExtra(EXTRA_YEAR))
                ? incomingIntent.getStringExtra(EXTRA_YEAR)
                : "N/A";
        final String websiteUrl = (incomingIntent != null && incomingIntent.hasExtra(EXTRA_WEBSITE_URL))
                ? incomingIntent.getStringExtra(EXTRA_WEBSITE_URL)
                : "https://www.moma.org/";

        String formattedDetails = "Title: " + title + "\n"
                + "Artist: " + artist + "\n"
                + "Year: " + year;
        detailsTextView.setText(formattedDetails);

        // Implicit Intent لفتح صفحة خارجية في المتصفح
        visitWebsiteButton.setOnClickListener(v -> {
            Uri webpage = Uri.parse(websiteUrl);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, webpage);
            try {
                startActivity(browserIntent);
            } catch (Exception e) {
                Toast.makeText(this, "No browser app available", Toast.LENGTH_SHORT).show();
            }
        });

        // العودة إلى الشاشة الرئيسية
        backButton.setOnClickListener(v -> finish());
    }
}