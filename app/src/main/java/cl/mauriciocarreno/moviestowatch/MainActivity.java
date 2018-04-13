package cl.mauriciocarreno.moviestowatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cl.mauriciocarreno.moviestowatch.models.Movie;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movieList;
    public static final String MOVIE_ID = "cl.mauriciocarreno.moviestowatch.KEY.MOVIE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText movieNameET = findViewById(R.id.movieNameET);

        Button saveBtn = findViewById(R.id.saveBtn);
        final Button lastMovieBtn = findViewById(R.id.lastMovieBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieName = movieNameET.getText().toString();

                if (movieName.trim().length() > 0) {
                    Movie movie = new Movie(movieName, false);
                    movie.save();

                    getMovies();
                    movieNameET.setText("");
                    Toast.makeText(MainActivity.this, "Se ha agregado la película", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Debe ingresar el nombre de la película", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lastMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int listSize = getMovies().size();
                long idMovie;

                if (listSize > 0) {
                    idMovie = getMovies().get(getMovies().size() - 1).getId();
                    Log.e("ID", String.valueOf(idMovie));
                    Intent lastMovieIntent = new Intent(MainActivity.this, MovieActivity.class);
                    lastMovieIntent.putExtra("ID", idMovie);
                    startActivity(lastMovieIntent);

                } else {
                    Toast.makeText(MainActivity.this, "No hay películas agregadas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<Movie> getMovies() {
        movieList = Movie.find(Movie.class, "watched = 0");
        return movieList;
    }

    @Override
    protected void onResume() {
        super.onResume();

        movieList = getMovies();
    }
}
