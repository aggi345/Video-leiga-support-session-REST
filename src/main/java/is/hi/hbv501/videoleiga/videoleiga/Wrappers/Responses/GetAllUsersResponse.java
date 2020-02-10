package is.hi.hbv501.videoleiga.videoleiga.Wrappers.Responses;

import is.hi.hbv501.videoleiga.videoleiga.Entities.User;

import java.util.List;

/************************
 * Höfundur: Kristján P.*
 ************************/
public class GetAllUsersResponse extends GenericResponse {

    private List<User> users;

    public GetAllUsersResponse(List<User> users) {
        this.users = users;
    }

    public GetAllUsersResponse(List<User> users, String message, List<?> errors) {
        super(message, errors);
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
