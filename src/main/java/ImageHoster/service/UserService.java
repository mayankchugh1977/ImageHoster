package ImageHoster.service;

import ImageHoster.model.User;
import ImageHoster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    //We are not currently storing the details of the user anywhere
    //We will be storing the user details in the Database & ORMs part
    public void registerUser(User newUser) {
        repository.registerUser(newUser);
    }

    //Since we do not have any user in the database, therefore the user with username 'upgrad' and password 'password' is hard-coded
    //This method returns true if the username is 'upgrad' and password is 'password'
    public User login(User user) {
        User existingUser = repository.checkUser(user.getUsername(), user.getPassword());
//        if (user.getUsername().equals("upgrad") && user.getPassword().equals("password"))
        if(existingUser!=null) {
            return existingUser;
        } else {
            return existingUser;
        }
    }

}