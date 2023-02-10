import java.util.ArrayList;

public class Genre {
    private String name;
    private ArrayList<Movie> moviesWithGenre;

    public Genre(String name, Movie firstMovie){
        this.name = name;
        this.moviesWithGenre = new ArrayList<>();
        moviesWithGenre.add(firstMovie);
    }

    public String getName(){
        return name;
    }

    public ArrayList<Movie> getMoviesWithGenre(){
        return moviesWithGenre;
    }

    public void addMovie(Movie movieToAdd){
        moviesWithGenre.add(movieToAdd);
        MovieCollection.sortResultsMovies(moviesWithGenre);
    }
}
