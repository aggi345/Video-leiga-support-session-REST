package is.hi.hbv501.videoleiga.videoleiga.Controllers;

import is.hi.hbv501.videoleiga.videoleiga.Entities.Genre;
import is.hi.hbv501.videoleiga.videoleiga.Entities.Movie;
import is.hi.hbv501.videoleiga.videoleiga.Entities.RentalLog;
import is.hi.hbv501.videoleiga.videoleiga.Entities.User;
import is.hi.hbv501.videoleiga.videoleiga.RequestWrappers.SearchRequestWrapper;
import is.hi.hbv501.videoleiga.videoleiga.Services.MovieService;
import is.hi.hbv501.videoleiga.videoleiga.Services.RentalLogService;
import is.hi.hbv501.videoleiga.videoleiga.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@RestController
public class HomeController {

    private MovieService movieService;
    private RentalLogService rentalLogService;
    private UserService userService;

    @Autowired
    public HomeController(MovieService movieService, RentalLogService rentalLogService, UserService userService){
        this.rentalLogService = rentalLogService;
        this.userService = userService;
        this.movieService = movieService;
    }

    /**
     * Home
     * @return All movies
     */
    @RequestMapping("/")
    public @ResponseBody List<Movie> Home(){
        return movieService.findAll();
    }


    /**
     * Request body sent as JSON
     * Looks like this:
     * {
     *     "title": title,
     *     "description": description,
     *     "rating": rating,
     *     "genres": [genres]
     * }
     * @param movie
     * @return The movie that was created
     */
    @RequestMapping(value ="/addmovie", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> addMovie(@Valid @RequestBody Movie movie, BindingResult result){
        if(result.hasErrors()){
            // Do something with errors
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(movieService.save(movie), HttpStatus.CREATED);
    }


    /**
     * No need to use
     */
    @RequestMapping(value="/addmovie", method = RequestMethod.GET)
    public String addMovieForm(Movie movie){
        return "add-movie";
    }

    /**
     *
     * @param id The movie ID
     * @return a valid StatusCode with a message
     */
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> deleteMovie(@PathVariable("id") long id){
        if (!movieService.findById(id).isPresent())
            return new ResponseEntity<>("No movie with given ID exists", HttpStatus.NOT_FOUND);

        movieService.findById(id).ifPresent(movie -> movieService.delete(movie));

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Create some data
     * @return All movies
     */
    @RequestMapping("/makedata")
    public @ResponseBody List<Movie> makeData(){
        HashSet<Genre> genres = new HashSet<>();
        genres.add(Genre.ADVENTURE);
        genres.add(Genre.ACTION);
        for (int i = 0; i < 3; i++) {
            this.movieService.save(new Movie("Great movie "+i,"fantastic movie in a trilogy",Double.valueOf(i),genres));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        User tempUser = new User("Karl Jóhann","pass123");
        List<Movie> tempMovie = movieService.findAll();
        this.userService.save(tempUser);
        try {
            rentalLogService.save(new RentalLog(tempMovie.get(0),tempUser,sdf.parse("21/12/2012"),sdf.parse("31/12/2013") ));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tempMovie;
    }

    @RequestMapping("/rentals")
    public @ResponseBody List<RentalLog> allRentals(){
        return rentalLogService.findAll();
    }

    /**
     * Not needed
     */
    @RequestMapping("/search")
    public String search(){
        return "search";
    }

    /**
     * You can create a wrapper for a request if the @RequestBody can not be
     * defined with just one of the Models.
     * Example:
     * public class RandomRequestWrapper {
     *     private Movie movie;
     *     private Reviews reviews;
     *     ...
     *     ...
     * }
     * @param searchRequestWrapper Wrapper to access the search string
     * @return All movies based on search string
     */
    @RequestMapping(value= "/movieSearch", method = RequestMethod.POST)
    public @ResponseBody List<Movie> searchMovie(@RequestBody SearchRequestWrapper searchRequestWrapper){
        return movieService.findByTitle(searchRequestWrapper.getSearch());
    }

    /**
     * No need to use
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUpGET(User user){
        return "signup";
    }

    /**
     * Sign up user
     * Request body example:
     * {
     *     "uName": username,
     *     "password": password
     * }
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> signUpPOST(@Valid @RequestBody User user, BindingResult result){
        HashMap<String, String> jsonMap = new HashMap<>();
        if(result.hasErrors()){
            jsonMap.put("message", "Error something something");
            return new ResponseEntity<>(jsonMap, HttpStatus.BAD_REQUEST);
        }
        User exists = userService.findByUName(user.uName);
        if(exists == null){
            userService.save(user);
        } else {
            jsonMap.put("message", "Username already taken");
            return new ResponseEntity<>(jsonMap, HttpStatus.BAD_REQUEST);
        }
        jsonMap.put("message", "User created successfully");
        return new ResponseEntity<>(jsonMap, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody List<User> usersGET(){
        return userService.findAll();
    }

    /**
     * No need to use
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(User user){
        return "login";
    }


    /**
     * Login user
     * Request object example:
     * {
     *     "uName": username,
     *     "password": password
     * }
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> loginPOST(@Valid @RequestBody User user, BindingResult result, HttpSession session){
        HashMap<String, String> jsonMap = new HashMap<>();
        if(result.hasErrors()){
            jsonMap.put("message", "Something something error");
            return new ResponseEntity<>(jsonMap, HttpStatus.BAD_REQUEST);
        }
        User exists = userService.login(user);
        if(exists != null){
            session.setAttribute("LoggedInUser", user);
            jsonMap.put("message","Login successful");
            return new ResponseEntity<>(jsonMap, HttpStatus.OK);
        }
        jsonMap.put("message", "Something something error figure out right StatusCode");
        return new ResponseEntity<>(jsonMap, HttpStatus.I_AM_A_TEAPOT);
    }


    /**
     * NOTE: User entity was edited to prevent password
     *       from being included in the response.
     */
    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> loggedinGET(HttpSession session){
        HashMap<String, String> jsonMap = new HashMap<>();

        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if(sessionUser  != null){
            return new ResponseEntity<>(sessionUser, HttpStatus.OK);
        }
        jsonMap.put("message", "You have to be logged in to visit this page");
        return new ResponseEntity<>(jsonMap, HttpStatus.UNAUTHORIZED);
    }
}
