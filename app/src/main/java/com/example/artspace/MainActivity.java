package com.example.artspace;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_INDEX = "current_artwork_index";

    private static class Artwork {
        final int imageResId;
        final String title;
        final String artist;
        final String year;
        final String websiteUrl;

        Artwork(int imageResId, String title, String artist, String year, String websiteUrl) {
            this.imageResId = imageResId;
            this.title = title;
            this.artist = artist;
            this.year = year;
            this.websiteUrl = websiteUrl;
        }
    }

    private Artwork[] artworks;
    private int currentIndex = 0;

    private TextView counterTextView;
    private ImageView artworkImageView;
    private TextView titleTextView;
    private TextView artistYearTextView;
    private Button previousButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initArtworks();
        initViews();

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX, 0);
        }

        if (currentIndex < 0) {
            currentIndex = 0;
        } else if (currentIndex >= artworks.length) {
            currentIndex = artworks.length - 1;
        }

        setupListeners();
        updateArtworkUI();
    }

    private void initArtworks() {
        artworks = new Artwork[]{
                new Artwork(
                        R.drawable.art_1,
                        "Starry Night Over the Rhone",
                        "Vincent van Gogh",
                        "1888",
                        "https://www.vangoghmuseum.nl/"
                ),
                new Artwork(
                        R.drawable.art_2,
                        "The Persistence of Memory",
                        "Salvador Dalí",
                        "1931",
                        "https://www.moma.org/"
                ),
                new Artwork(
                        R.drawable.art_3,
                        "Girl with a Pearl Earring",
                        "Johannes Vermeer",
                        "1665",
                        "https://www.mauritshuis.nl/"
                ),
                new Artwork(
                        R.drawable.art_4,
                        "The Great Wave off Kanagawa",
                        "Katsushika Hokusai",
                        "1831",
                        "https://www.metmuseum.org/"
                ),
                new Artwork(
                        R.drawable.art_5,
                        "Water Lilies",
                        "Claude Monet",
                        "1919",
                        "https://www.louvre.fr/"
                ),
                new Artwork(
                        R.drawable.art_6,
                        "The Scream",
                        "Edvard Munch",
                        "1893",
                        "https://www.nasjonalmuseet.no/"
                )
        };
    }

    private void initViews() {
        counterTextView = findViewById(R.id.tv_counter);
        artworkImageView = findViewById(R.id.iv_artwork);
        titleTextView = findViewById(R.id.tv_title);
        artistYearTextView = findViewById(R.id.tv_artist_year);
        previousButton = findViewById(R.id.btn_previous);
        nextButton = findViewById(R.id.btn_next);
    }

    private void setupListeners() {
        previousButton.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateArtworkUI();
            }
        });

        nextButton.setOnClickListener(v -> {
            if (currentIndex < artworks.length - 1) {
                currentIndex++;
                updateArtworkUI();
            }
        });

        artworkImageView.setOnClickListener(v -> {
            Artwork currentArtwork = artworks[currentIndex];
            Intent intent = new Intent(MainActivity.this, ArtworkDetailsActivity.class);
            intent.putExtra(ArtworkDetailsActivity.EXTRA_TITLE, currentArtwork.title);
            intent.putExtra(ArtworkDetailsActivity.EXTRA_ARTIST, currentArtwork.artist);
            intent.putExtra(ArtworkDetailsActivity.EXTRA_YEAR, currentArtwork.year);
            intent.putExtra(ArtworkDetailsActivity.EXTRA_WEBSITE_URL, currentArtwork.websiteUrl);
            startActivity(intent);
        });
    }

    private void updateArtworkUI() {
        Artwork currentArtwork = artworks[currentIndex];

        counterTextView.setText((currentIndex + 1) + " / " + artworks.length);
        artworkImageView.setImageResource(currentArtwork.imageResId);
        artworkImageView.setContentDescription(currentArtwork.title);
        titleTextView.setText(currentArtwork.title);
        artistYearTextView.setText(currentArtwork.artist + " (" + currentArtwork.year + ")");

        previousButton.setEnabled(currentIndex > 0);
        nextButton.setEnabled(currentIndex < artworks.length - 1);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }
}