package is.hi.hbv501.videoleiga.videoleiga.Services.Implementations;

import is.hi.hbv501.videoleiga.videoleiga.Entities.User;
import is.hi.hbv501.videoleiga.videoleiga.Repositories.UserRepository;
import is.hi.hbv501.videoleiga.videoleiga.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    UserRepository repository;

    @Autowired
    public UserServiceImplementation(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findByUName(String uName) {
        return repository.findByUName(uName);
    }

    @Override
    public User login(User user) {
        User exists = findByUName(user.uName);
        if(exists != null){
            if(exists.password.equals(user.password)){
                return user;
            }
        }
        return null;
    }
}