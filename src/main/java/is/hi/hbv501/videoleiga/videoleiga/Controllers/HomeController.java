package is.hi.hbv501.videoleiga.videoleiga.Controllers;

import is.hi.hbv501.videoleiga.videoleiga.Entities.Genre;
import is.hi.hbv501.videoleiga.videoleiga.Entities.Movie;
import is.hi.hbv501.videoleiga.videoleiga.Entities.RentalLog;
import is.hi.hbv501.videoleiga.videoleiga.Entities.User;
import is.hi.hbv501.videoleiga.videoleiga.Wrappers.Responses.*;
import is.hi.hbv501.videoleiga.videoleiga.Wrappers.Requests.SearchRequestWrapper;
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
import java.util.*;

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
    public ResponseEntity<GetAllMoviesResponse> Home(){
        return new ResponseEntity<>(new GetAllMoviesResponse(movieService.findAll()), HttpStatus.OK);
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
    public ResponseEntity<AddMovieResponse> addMovie(@Valid @RequestBody Movie movie, BindingResult result){
        if(result.hasErrors()){
            // Do something with errors
            return new ResponseEntity<>(new AddMovieResponse(null, result.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new AddMovieResponse(movieService.save(movie)), HttpStatus.CREATED);
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
    @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DeleteMovieResponse> deleteMovie(@PathVariable("id") long id){
        if (!movieService.findById(id).isPresent()) {
            List<String> errors = new ArrayList<>();
            errors.add("No movie with id: " + id + " exists");
            return new ResponseEntity<>(new DeleteMovieResponse(null, errors), HttpStatus.NOT_FOUND);
        }
        movieService.findById(id).ifPresent(movie -> movieService.delete(movie));

        return new ResponseEntity<>(new DeleteMovieResponse(), HttpStatus.OK);
    }

    /**
     * Create some data
     * @return All movies
     */
    @RequestMapping("/makedata")
    public ResponseEntity<GetAllMoviesResponse> makeData(){
        HashSet<Genre> genres = new HashSet<>();
        genres.add(Genre.ADVENTURE);
        genres.add(Genre.ACTION);
        for (int i = 0; i < 4; i++) {
            this.movieService.save(new Movie("Great movie "+i,"fantastic movie in a trilogy",Double.valueOf(i),genres));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        User tempUser1 = new User("Karl Jóhann","pass123");
        User tempUser2 = new User("Jóhann Karl","pass123");
        List<Movie> tempMovie = movieService.findAll();
        this.userService.save(tempUser1);
        this.userService.save(tempUser2);
        try {
            rentalLogService.save(new RentalLog(tempMovie.get(0),tempUser1,sdf.parse("21/12/2012"),sdf.parse("31/12/2013") ));
            rentalLogService.save(new RentalLog(tempMovie.get(1),tempUser1,sdf.parse("21/12/2012"),sdf.parse("31/12/2013") ));
            rentalLogService.save(new RentalLog(tempMovie.get(2),tempUser2,sdf.parse("21/12/2012"),sdf.parse("31/12/2013") ));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new GetAllMoviesResponse(movieService.findAll()), HttpStatus.CREATED);
    }

    /**
     * Return all rentals
     */
    @RequestMapping("/rentals")
    public @ResponseBody ResponseEntity<GetAllRentalsResponse> allRentals(){
        return new ResponseEntity<>(new GetAllRentalsResponse(rentalLogService.findAll()), HttpStatus.OK);
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
    public ResponseEntity<SearchResponse> searchMovie(@RequestBody SearchRequestWrapper searchRequestWrapper){
        List<Movie> movies = movieService.findByTitle(searchRequestWrapper.getSearch());
        return new ResponseEntity<>(new SearchResponse(movies), HttpStatus.OK);
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
    public ResponseEntity<LoginAndSignUpResponse> signUpPOST(@Valid @RequestBody User user, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(new LoginAndSignUpResponse(user, null, result.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        User exists = userService.findByUName(user.uName);
        if(exists == null){
            userService.save(user);
        } else {
            List<String> errors = new ArrayList<>();
            errors.add("Username already taken");
            return new ResponseEntity<>(new LoginAndSignUpResponse(user, null, errors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new LoginAndSignUpResponse(user, "User created successfully", null), HttpStatus.CREATED);
    }

    /**
     * Return all users
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<GetAllUsersResponse> usersGET(){
        return new ResponseEntity<>(new GetAllUsersResponse(userService.findAll()), HttpStatus.OK);
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
    public ResponseEntity<LoginAndSignUpResponse> loginPOST(@Valid @RequestBody User user, BindingResult result, HttpSession session){
        if(result.hasErrors()){
            return new ResponseEntity<>(new LoginAndSignUpResponse(user, null, result.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
        User exists = userService.login(user);
        if(exists != null){
            session.setAttribute("LoggedInUser", user);
            return new ResponseEntity<>(new LoginAndSignUpResponse(user, "Login successful",null), HttpStatus.OK);
        }
        List<String> errors = new ArrayList<>();
        errors.add("Login unsuccessful");
        return new ResponseEntity<>(new LoginAndSignUpResponse(user, null, errors), HttpStatus.BAD_REQUEST);
    }


    /**
     * NOTE: User entity was edited to prevent password
     *       from being included in the response.
     */
    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public ResponseEntity<GetUserResponse> loggedinGET(HttpSession session){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if(sessionUser  != null){
            return new ResponseEntity<>(new GetUserResponse(sessionUser), HttpStatus.OK);
        }
        List<String> errors = new ArrayList<>();
        errors.add("You must be logged in to visit this page");
        return new ResponseEntity<>(new GetUserResponse(null, null, errors ), HttpStatus.UNAUTHORIZED);
    }
}
