package com.aircondition.air.Controller;

import com.aircondition.air.Model.User;
import com.aircondition.air.Repository.UserRepository;
import com.aircondition.air.Service.UserService;
import com.aircondition.air.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserServiceImpl userServiceImpl;

    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/dashboard"}, method = RequestMethod.GET)
    public String homePage(Model model) {
//        List<User> users = userService.getAllUsers();
//        model.addAttribute("users", users);
        return "user/dashboard";
    }
    @GetMapping("/user-view")
    public String viewUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        // Additional logic
        return "admin/dashboard";
    }

    @GetMapping("/edit-user/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id).orElse(null);
        model.addAttribute("user", user);
        return "user/edit-user";
    }

    @PostMapping("/edit-user/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute User updatedUser) {
        userService.saveUser(updatedUser);
        return "redirect:/user/dashboard";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUserConfirmation(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id).orElse(null);
        model.addAttribute("user", user);
        return "user/delete-user";
    }

    @PostMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/user/dashboard";
    }
}
