package com.aircondition.air.Service;

import com.aircondition.air.Model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();


    void saveUser(User user);
    void updateUserById(String email, User updatedUser);

    static void updatePassword(User user, String newPassword) {
    }

    void updateUserByEmail(String email, User updatedUser);

    List<Object> isUserPresent(User user);

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    // Methods from the provided interface
    Optional<User> getUserById(Long id);

    void deleteUserById(Long id);

    // Other methods...
}
