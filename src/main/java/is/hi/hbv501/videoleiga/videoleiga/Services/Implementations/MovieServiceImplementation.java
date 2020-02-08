package is.hi.hbv501.videoleiga.videoleiga.Services.Implementations;

import is.hi.hbv501.videoleiga.videoleiga.Entities.Movie;
import is.hi.hbv501.videoleiga.videoleiga.Repositories.MovieRepository;
import is.hi.hbv501.videoleiga.videoleiga.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImplementation implements MovieService {

    MovieRepository repository;

    @Autowired
    public MovieServiceImplementation(MovieRepository movieRepository){this.repository = movieRepository;}

    @Override
    public Movie save(Movie movie) {
        return repository.save(movie);
    }

    @Override
    public void delete(Movie movie) {
        repository.delete(movie);
    }

    @Override
    public List<Movie> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Movie> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Movie> findByTitle(String title) {

        return repository.findByTitle(title);
    }
}
