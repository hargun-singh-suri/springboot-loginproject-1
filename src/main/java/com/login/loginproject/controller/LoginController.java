package com.login.loginproject.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.login.loginproject.model.Role;
import com.login.loginproject.model.User;
import com.login.loginproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoginController {

    @Autowired
    private UserService userService;

    //Return Login Page
    @RequestMapping(value = {"/", "login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    //Return Registration page, sending object of user to get the values set in the user properties during registration
    @RequestMapping(value = {"/registration"}, method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject(user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    //Once User perform submit on Registration page below API will be called data will be stored in Apache Derby
    @RequestMapping(value = {"/registration"}, method = RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExist = userService.getUserByEmail(user.getEmail());
        if (userExist != null) {
            bindingResult.rejectValue("email", "error.user",
                    "There is already a user registered with the email provided");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    //Home page for User with Admin role
    @RequestMapping(value = {"/admin/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        //Security related Code
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }


    //Out of Box API's to check user/role data or to save user/role data to Apache Derby

    @RequestMapping(value = "/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping("/roles")
    public List<Role> getAllRoles() {
        return userService.getAllRoles();
    }

    @RequestMapping("/user/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @RequestMapping("/role/{id}")
    public Role getRole(@PathVariable int id) {
        return userService.getRole(id);
    }

    @RequestMapping("/role/name/{roleName}")
    public Role getByRoleName(@PathVariable String roleName) {
        return userService.getByRoleName(roleName);
    }

    @RequestMapping("/user/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void saveUserFromJson(@RequestBody User user) {
        userService.saveUserFromJson(user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/role")
    public void saveRoleFromJson(@RequestBody Role role) {
        userService.saveRoleFromJson(role);
    }


}
