package is.hi.hbv501.videoleiga.videoleiga.Services;

import is.hi.hbv501.videoleiga.videoleiga.Entities.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    Movie save(Movie movie);
    void delete(Movie movie);
    List<Movie> findAll();
    Optional<Movie> findById(long id);
    List<Movie> findByTitle(String title);
}
