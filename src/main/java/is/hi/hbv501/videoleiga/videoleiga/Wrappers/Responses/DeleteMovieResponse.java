package is.hi.hbv501.videoleiga.videoleiga.Wrappers.Responses;

import org.springframework.validation.ObjectError;

import java.util.List;

/************************
 * Höfundur: Kristján P.*
 ************************/
public class DeleteMovieResponse extends GenericResponse {

    public DeleteMovieResponse(){}

    public DeleteMovieResponse(String message, List<?> errors) {
        super(message, errors);
    }
}
