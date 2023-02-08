import java.util.ArrayList;

public class Actor {
    private String name;
    private ArrayList<Movie> moviesStarred;

    public Actor(String name, Movie firstMovie){
        this.name = name;
        this.moviesStarred = new ArrayList<>();
        moviesStarred.add(firstMovie);
    }

    public void addMovie(Movie movieToAdd){
        moviesStarred.add(movieToAdd);
        MovieCollection.sortResults(moviesStarred);
    }
}
