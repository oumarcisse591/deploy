package com.deployment.deploy.Controller;

import com.deployment.deploy.Dao.UserDao;
import com.deployment.deploy.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private UserDao userDao;

    @Autowired
    public UserController(UserDao theUserdao){ userDao = theUserdao;}

    @GetMapping("/list-user")
    public String listUser(Model theModel){
        List<User> theUsers = userDao.findAllUser();
        theModel.addAttribute("users", theUsers);
        return "user/list-user";
    }

    @GetMapping("/add-user")
    public String addUser(Model theModel){
        User theUser = new User();
        theModel.addAttribute("user", theUser);
        return "user/create-user";
    }

    @PostMapping("/create-user")
    public String saveUser(@ModelAttribute("user") User theUser){
        userDao.saveUser(theUser);
        return "redirect:/list-user";
    }

    @GetMapping("/update-user")
    public String updateUser(@RequestParam("id") int theId, Model theModel){
        User theUser = userDao.findUserById(theId);
        theModel.addAttribute("user", theUser);
        return "user/user-update";
    }

    @GetMapping("/delete-user")
    public String deleteUser(@RequestParam("id") int theId){
        userDao.deleteUserById(theId);
        return "redirect:/list-user";
    }
}
