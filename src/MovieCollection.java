import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
  private ArrayList<Movie> movies;
  private Scanner scanner;

  private ArrayList<Actor> listOfAllActors;
  private ArrayList<Genre> listOfAllGenre;


  public MovieCollection(String fileName) {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
    this.listOfAllActors = new ArrayList<Actor>();
    createActorList();
    this.listOfAllGenre = new ArrayList<>();
    createGenreList();
  }

  public ArrayList<Movie> getMovies() {
    return movies;
  }

  public void menu() {
    String menuOption = "";

    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");

    while (!menuOption.equals("q")) {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();

      if (menuOption.equals("t")) {
        searchTitles();
      } else if (menuOption.equals("c")) {
        searchCast();
      } else if (menuOption.equals("k")) {
        searchKeywords();
      } else if (menuOption.equals("g")) {
        listGenres();
      } else if (menuOption.equals("r")) {
        listHighestRated();
      } else if (menuOption.equals("h")) {
        listHighestRevenue();
      } else if (menuOption.equals("q")) {
        System.out.println("Goodbye!");
      } else {
        System.out.println("Invalid choice!");
      }
    }
  }

  private void importMovieList(String fileName) {
    try {
      movies = new ArrayList<Movie>();
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      while ((line = bufferedReader.readLine()) != null) {
        // get data from the columns in the current row and split into an array
        String[] movieFromCSV = line.split(",");

        /* TASK 1: FINISH THE CODE BELOW */
        // using the movieFromCSV array,
        // obtain the title, cast, director, tagline,
        // keywords, overview, runtime (int), genres,
        // user rating (double), year (int), and revenue (int)
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);

        // create a Movie object with the row data:
        Movie movieToAdd = new Movie(title, cast, director, tagline, keywords,
                overview, runtime, genres, userRating, year, revenue);

        // add the Movie to movies:
        movies.add(movieToAdd);

      }
      bufferedReader.close();
    } catch(IOException exception) {
      System.out.println("Unable to access " + exception.getMessage());
    }
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResultsMovies(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  public static void sortResultsMovies(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortResultsActors(ArrayList<Actor> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Actor temp = listToSort.get(j);
      String tempTitle = temp.getName();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getName()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortResultsGenre(ArrayList<Genre> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Genre temp = listToSort.get(j);
      String tempTitle = temp.getName();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getName()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  
  private void displayMovieInfo(Movie movie) {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchKeywords() {
    System.out.print("Enter a key search term: ");
    String userKey = scanner.nextLine();

    //prevent case sensitivity
    userKey = userKey.toLowerCase();

    ArrayList<Movie> results = new ArrayList<>();

    for (Movie movie : movies){
      String movieKeywords = movie.getKeywords().toLowerCase();
      if (movieKeywords.contains(userKey)){
        results.add(movie);
      }
    }

    if (results.size() > 0){
      sortResultsMovies(results);
      for (int i = 0; i < results.size(); i ++){
        System.out.println(i + 1 + ". " + results.get(i).getTitle());
      }
      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie toView = results.get(choice - 1);
      displayMovieInfo(toView);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie keywords match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
;
  }

  private void searchCast() {
    System.out.print("Enter a person to search for (first or last name): ");
    String userKey = scanner.nextLine().toLowerCase();

    ArrayList<Actor> resultsNames = new ArrayList<>();

    for (Actor currentActor : listOfAllActors) {
      if (currentActor.getName().toLowerCase().contains(userKey)) {
        resultsNames.add(currentActor);
      }
    }
    if (resultsNames.size() != 0) {

      for (int i = 0; i < resultsNames.size(); i++) {
        System.out.println(i + 1 + ". " + resultsNames.get(i).getName());
      }

      System.out.println("Which actor would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Actor actorToView = resultsNames.get(choice - 1);

      ArrayList<Movie> moviesStarred = actorToView.getMoviesStarred();
      System.out.println();

      for (int i = 0; i < moviesStarred.size(); i++) {
        System.out.println(i + 1 + ". " + moviesStarred.get(i).getTitle());
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choiceMovie = scanner.nextInt();
      scanner.nextLine();
      Movie toView = moviesStarred.get(choiceMovie - 1);
      displayMovieInfo(toView);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo actors match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }


  
  private void listGenres() {

    for (int i = 0; i < listOfAllGenre.size(); i++) {
      System.out.println(i + 1 + ". " + listOfAllGenre.get(i).getName());
    }

    System.out.println("Which genre would you like to look at?");
    System.out.print("Enter number: ");
    int choice = scanner.nextInt();
    scanner.nextLine();
    Genre genreToView = listOfAllGenre.get(choice - 1);

    ArrayList<Movie> moviesWithCurrentGenre = genreToView.getMoviesWithGenre();
    System.out.println();

    for (int i = 0; i < moviesWithCurrentGenre.size(); i++) {
      System.out.println(i + 1 + ". " + moviesWithCurrentGenre.get(i).getTitle());
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    int choiceMovie = scanner.nextInt();
    scanner.nextLine();
    Movie toView = moviesWithCurrentGenre.get(choiceMovie - 1);
    displayMovieInfo(toView);
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();

  }
  
  private void listHighestRated() {
    ArrayList<Movie> results = new ArrayList<>(movies);

    for (int j = 1; j < results.size(); j++) {
      Movie temp = results.get(j);
      double tempRating = temp.getUserRating();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempRating > results.get(possibleIndex - 1).getUserRating()) {
        results.set(possibleIndex, results.get(possibleIndex - 1));
        possibleIndex--;
      }
      results.set(possibleIndex, temp);
    }

    Movie[] top50Ratings = new Movie[50];
    for (int k = 0; k < 50; k ++){
      top50Ratings[k] = results.get(k);
    }

    for (int i = 0; i < top50Ratings.length; i++) {
      System.out.println(i + 1 + ". " + top50Ratings[i].getTitle() + ": " + top50Ratings[i].getUserRating());
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    int choiceMovie = scanner.nextInt();
    scanner.nextLine();
    Movie toView = top50Ratings[choiceMovie - 1];
    displayMovieInfo(toView);
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();

  }
  
  private void listHighestRevenue() {
    //copy of movies list
    ArrayList<Movie> results = new ArrayList<>(movies);

    //orders results list by revenue (greatest to least)
    for (int j = 1; j < results.size(); j++) {
      Movie temp = results.get(j);
      double tempRevenue = temp.getRevenue();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempRevenue > results.get(possibleIndex - 1).getRevenue()) {
        results.set(possibleIndex, results.get(possibleIndex - 1));
        possibleIndex--;
      }
      results.set(possibleIndex, temp);
    }

    //stores only top 50 movies
    Movie[] top50Revenue = new Movie[50];
    for (int k = 0; k < 50; k ++){
      top50Revenue[k] = results.get(k);
    }

    //prints the top 50
    for (int i = 0; i < top50Revenue.length; i++) {
      System.out.println(i + 1 + ". " + top50Revenue[i].getTitle() + ": " + top50Revenue[i].getRevenue());
    }

    //user picks to learn more about a movie
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    int choiceMovie = scanner.nextInt();
    scanner.nextLine();
    Movie toView = top50Revenue[choiceMovie - 1];
    displayMovieInfo(toView);
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  /*
  a one-time method for creating a list of all actors that appear in all movies in the database
  each actor object will have the movies they starred in
   */
  private void createActorList(){
    for (int i = 0; i < movies.size(); i ++)
    {
      Movie currentMovie = movies.get(i);
      String actors = currentMovie.getCast();
      String[] currentMovieCast = actors.split("\\|");
      for (String actorName : currentMovieCast)
      {
        //only for the first movie, create Actor objects for all the cast
        if (i == 0)
        {
          listOfAllActors.add(new Actor(actorName, currentMovie));
        }
        else
        {
          boolean added = false;
          for (int j = 0; j < listOfAllActors.size(); j++)
          {
            //if there's already an Actor object created, add the currentMovie to moviesStarred for the actor
            if (listOfAllActors.get(j).getName().equals(actorName))
            {
              listOfAllActors.get(j).addMovie(currentMovie);
              added = true;
            }
          }
          //if actor isn't on the actor list, create a new actor object
          if (!added)
          {
            listOfAllActors.add(new Actor(actorName, currentMovie));
          }
        }
      }
    }
    sortResultsActors(listOfAllActors);
    System.out.println("num of all actors combined: " + listOfAllActors.size());
  }


  private void createGenreList(){
    for (int i = 0; i < movies.size(); i ++)
    {
      Movie currentMovie = movies.get(i);
      String[] currentMovieGenres = currentMovie.getGenres().split("\\|");
      for (String genreName : currentMovieGenres)
      {
        //only for the first movie, create Actor objects for all the cast
        if (i == 0)
        {
          listOfAllGenre.add(new Genre(genreName, currentMovie));
        }
        else
        {
          boolean added = false;
          for (int j = 0; j < listOfAllGenre.size(); j++)
          {
            //if there's already a Genre object created, add the currentMovie to moviesWithGenre for the genre
            if (listOfAllGenre.get(j).getName().equals(genreName))
            {
              listOfAllGenre.get(j).addMovie(currentMovie);
              added = true;
            }
          }
          //if genre isn't on the genre list, create a new actor object
          if (!added)
          {
            listOfAllGenre.add(new Genre(genreName, currentMovie));
          }
        }
      }
    }
    sortResultsGenre(listOfAllGenre);
    System.out.println("num of all genres combined: " + listOfAllGenre.size());
  }
}