package com.aircondition.air.Controller;

import com.aircondition.air.Model.User;
import com.aircondition.air.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String adminHome(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/dashboard";
    }

    @GetMapping("/edit-user/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        user.ifPresent(u -> model.addAttribute("user", u));
        return "admin/edit-user";
    }

    @PutMapping ("/edit-user/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute User editedUser) {
        // Validation and update logic
        userService.saveUser(editedUser);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUserConfirmation(@PathVariable Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        user.ifPresent(u -> model.addAttribute("user", u));
        return "admin/delete-user";
    }

    @PostMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        // Delete logic
        userService.deleteUserById(id);
        return "redirect:/admin/dashboard";
    }
}


