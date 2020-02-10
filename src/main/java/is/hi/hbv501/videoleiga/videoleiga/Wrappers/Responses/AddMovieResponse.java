package is.hi.hbv501.videoleiga.videoleiga.Wrappers.Responses;

import is.hi.hbv501.videoleiga.videoleiga.Entities.Movie;
import org.springframework.validation.ObjectError;

import java.util.List;

/************************
 * Höfundur: Kristján P.*
 ************************/
public class AddMovieResponse extends GenericResponse {
    private Movie movie;

    public AddMovieResponse(){}

    public AddMovieResponse(Movie movie) {
        this.movie = movie;
    }

    public AddMovieResponse(String message, List<?> errors) {
        this(null, message, errors);
    }
    public AddMovieResponse(Movie movie, String message, List<?> errors) {
        super(message, errors);
        this.movie = movie;
    }


    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
