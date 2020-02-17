package is.hi.hbv501.videoleiga.videoleiga.Controllers;

import com.sun.org.apache.regexp.internal.RESyntaxException;
import is.hi.hbv501.videoleiga.videoleiga.Entities.Genre;
import is.hi.hbv501.videoleiga.videoleiga.Entities.Movie;
import is.hi.hbv501.videoleiga.videoleiga.Entities.RentalLog;
import is.hi.hbv501.videoleiga.videoleiga.Entities.User;
import is.hi.hbv501.videoleiga.videoleiga.Services.MovieService;
import is.hi.hbv501.videoleiga.videoleiga.Services.RentalLogService;
import is.hi.hbv501.videoleiga.videoleiga.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/simple")
public class SimpleController {

    private MovieService movieService;
    private RentalLogService rentalLogService;
    private UserService userService;

    @Autowired
    public SimpleController(MovieService movieService, RentalLogService rentalLogService, UserService userService) {
        this.rentalLogService = rentalLogService;
        this.userService = userService;
        this.movieService = movieService;
    }

    @RequestMapping("/movies")
    public List<Movie> Home(@RequestParam(value = "search", required = false) String search) {
        if(search != null)
            return movieService.findByTitle(search);
        else
            return movieService.findAll();
    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    public Movie addMovie(@Valid @RequestBody Movie movie, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid movie");
        }
        return movieService.save(movie);
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMovie(@PathVariable("id") long id) {
        Movie movie = movieService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movies not found"));
        movieService.delete(movie);
        return ResponseEntity.noContent().build();
    }
    /* Not needed
    @RequestMapping(value = "/addmovie", method = RequestMethod.GET)
    public String addMovieForm(Movie movie) {
        return "add-movie";
    }
    */

    @RequestMapping("/makedata")
    public List<Movie> makeData() {
        HashSet<Genre> genres = new HashSet<>();
        genres.add(Genre.ADVENTURE);
        genres.add(Genre.ACTION);
        for (int i = 0; i < 3; i++) {
            this.movieService
                    .save(new Movie("Great movie " + i, "fantastic movie in a trilogy", Double.valueOf(i), genres));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        User tempUser = new User("Karl Jóhann", "pass123");
        List<Movie> tempMovie = movieService.findAll();
        this.userService.save(tempUser);
        try {
            rentalLogService
                    .save(new RentalLog(tempMovie.get(0), tempUser, sdf.parse("21/12/2012"), sdf.parse("31/12/2013")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tempMovie;
    }

    @RequestMapping("/rentals")
    public List<RentalLog> allRentals() {
        return rentalLogService.findAll();
    }

    /* Not needed
    @RequestMapping("/search")
    public String search() {
        return "search";
    }
    */

    /* Not needed
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUpGET(User user) {
        return "signup";
    }*/

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public User signUpPOST(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error...");
        }
        User exists = userService.findByUName(user.uName);
        if (exists == null)
            return userService.save(user);
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username unavailable");
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> usersGET() {
        return userService.findAll();
    }

    /* Not needed
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(User user) {
        return "login";
    }*/

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User loginPOST(@Valid User user, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }
        User exists = userService.login(user);
        if (exists != null) {
            session.setAttribute("LoggedInUser", user);
            return exists;
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login unsuccessful");
    }

    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public User loggedinGET(HttpSession session) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser != null) {
            return sessionUser;
        }
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You have to be logged in");
    }
}
