package is.hi.hbv501.videoleiga.videoleiga.Wrappers.Responses;

import is.hi.hbv501.videoleiga.videoleiga.Entities.Movie;
import org.springframework.validation.ObjectError;

import java.util.List;

/************************
 * Höfundur: Kristján P.*
 ************************/
public class SearchResponse extends GenericResponse {
    private List<Movie> movies;

    public SearchResponse(){}

    public SearchResponse(List<Movie> movies) {
        this.movies = movies;
    }
    public SearchResponse(List<Movie> movies, String message, List<?> errors) {
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
