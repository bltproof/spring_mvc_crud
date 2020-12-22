package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/")
    public String showAllUsers(@ModelAttribute("user") User user, Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping(value = "/show/{id}")
    public String showUser(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "/show";
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.delete(user);
        return "redirect:/";
    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping(value = "/new")
    public String add(Model model) {
        User user = new User();
        Role role = new Role();
        model.addAttribute("user", user);
        model.addAttribute("role", role);
        model.addAttribute("roles", roleService.listRoles());
        return "/new";
    }

    @PostMapping(value = "/new")
    public String addUser(Model model, @ModelAttribute("user") User user, @ModelAttribute("role") Role role) {
        Role newRole = roleService.listRoles()
                .stream()
                .filter(x -> x.getName().equals(role.getName()))
                .findAny().get();

        User obj = new User(
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                user.getAge(),
                user.getEmail());

        obj.setRole(newRole);
        userService.add(obj);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/";
    }

    @GetMapping(value = "/edit/{id}")
    public String editUser(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);

        Role role = user.getRoles().stream().findFirst().orElse(new Role());

        model.addAttribute("user", user);
        model.addAttribute("role", role);
        model.addAttribute("roles", roleService.listRoles());
        return "/edit";
    }

    @PatchMapping(value = "/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, @ModelAttribute Role role) {
        Role newRole = roleService.listRoles()
                .stream()
                .filter(x -> x.getName().equals(role.getName()))
                .findAny().get();

        User obj = userService.getAllUsers()
                .stream()
                .filter(u -> u.getId() == user.getId())
                .findAny()
                .get();

        obj.setUsername(user.getUsername());
        obj.setPassword(passwordEncoder.encode(user.getPassword()));
        obj.setAge(user.getAge());
        obj.setEmail(user.getEmail());

        obj.setRole(newRole);

        userService.update(obj);

        return "redirect:/";
    }
}