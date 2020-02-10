package is.hi.hbv501.videoleiga.videoleiga.Wrappers.Responses;

import is.hi.hbv501.videoleiga.videoleiga.Entities.User;

import java.util.List;

/************************
 * Höfundur: Kristján P.*
 ************************/
public class LoginAndSignUpResponse extends GenericResponse {

    private User user;

    public LoginAndSignUpResponse(User user){
        this.user = user;
    }

    public LoginAndSignUpResponse(User user, String message, List<?> errors) {
        super(message, errors);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
