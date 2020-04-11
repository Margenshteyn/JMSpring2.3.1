package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userAdd")
    public String addUser(ModelMap model, User user) {
        System.out.println(user.getAmount());
        if ("".equals(user.getLogin())) {
            model.addAttribute("message", "Login field couldn't be empty!");
        } else if (userService.addUser(user)) {
            model.addAttribute("message", "User: " + user.getLogin() + " added!");
        } else {
            model.addAttribute("message", "User: " + user.getLogin() + " not added - user already exist!");
        }
        return "redirect:/list";
    }

    @GetMapping("/userDelete")
    public String deleteUser(ModelMap model, String login) {
        userService.deleteUser(login);
        model.addAttribute("message", "User: " + login + " deleted!");
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String getAllUsers(ModelMap model) {
        model.addAttribute("usersList", userService.getUsersList());
        return "list";
    }

    @GetMapping("/userUpdate")
    public String getUpdateForm() {
        return "userUpdate";
    }

    @PostMapping("/userUpdate")
    public String updateUser(ModelMap model, String password, String newLogin, String newPassword, String newName, double newAmount) {
        User newUser = new User(newLogin, newPassword, newName, newAmount);
        if (userService.updateUser(newUser, password)) {
            model.addAttribute("message", "User: " + newLogin + " updated!");
        } else {
            model.addAttribute("message", "User: " + newLogin + " not updated - login/password wrong!");
        }
        return "redirect:/list";
    }

}
