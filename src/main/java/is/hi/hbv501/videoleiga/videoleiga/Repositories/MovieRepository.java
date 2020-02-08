package is.hi.hbv501.videoleiga.videoleiga.Repositories;


import is.hi.hbv501.videoleiga.videoleiga.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie save(Movie movie);
    void delete(Movie movie);
    List<Movie> findAll();
    List<Movie> findByTitle(String title);
    Optional<Movie> findById(long id);
}
