package is.hi.hbv501.videoleiga.videoleiga.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;
    private Double rating;

    @ElementCollection(targetClass=Genre.class)
    @Column(name="genre", nullable=false)
    @CollectionTable(name="movie_genres", joinColumns= {@JoinColumn(name="movie_id")})
    public Set<Genre> genres;

    @OneToMany(mappedBy = "movie")
    private List<RentalLog> rentals = new ArrayList<>();

    public Movie() {
    }

    public Movie(String title, String description, Double rating, HashSet<Genre> genres) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.genres = genres;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
