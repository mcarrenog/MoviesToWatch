package cl.mauriciocarreno.moviestowatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import cl.mauriciocarreno.moviestowatch.models.Movie;

public class MovieActivity extends AppCompatActivity {

    private static final String MOVIE_NAME = "";
    private static long MOVIE_ID = 0;

    private CheckBox watched;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        TextView lastMovieNameTV = findViewById(R.id.lastMovieNameET);

        watched = findViewById(R.id.watchedCB);
        MOVIE_ID = getIntent().getLongExtra("ID", 0);
        Log.e("ID RECIBIDA", String.valueOf(MOVIE_ID));
        movie = Movie.findById(Movie.class, MOVIE_ID);
        getSupportActionBar().setTitle(movie.getName());
        lastMovieNameTV.setText(movie.getName());

    }

    @Override
    protected void onPause() {
        boolean watchedStatus = watched.isChecked();
        Log.d("ID","Entré a la pausa y el valor es: " + watchedStatus);
        super.onPause();
        movie.setWatched(watchedStatus);
        movie.save();
    }
}
