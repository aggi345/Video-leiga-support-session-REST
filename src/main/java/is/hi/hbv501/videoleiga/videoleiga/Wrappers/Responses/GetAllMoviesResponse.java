package is.hi.hbv501.videoleiga.videoleiga.Wrappers.Responses;

import is.hi.hbv501.videoleiga.videoleiga.Entities.Movie;
import org.springframework.validation.ObjectError;

import java.util.List;

/************************
 * Höfundur: Kristján P.*
 ************************/
public class GetAllMoviesResponse extends GenericResponse {

    private List<Movie> movies;

    public GetAllMoviesResponse(List<Movie> movies) {
        this.movies = movies;
    }

    public GetAllMoviesResponse(List<Movie> movies, String message, List<ObjectError> errors) {
        super(message, errors);
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
