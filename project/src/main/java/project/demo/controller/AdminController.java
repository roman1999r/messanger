package project.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.demo.service.UserService;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

//    @GetMapping("/admins")
//    public String userList(Model model) {
//        model.addAttribute("allUsers", userService.allUsers());
//        return "administration";
//    }
//
//    @PostMapping("/admins")
//    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
//                              @RequestParam(required = true, defaultValue = "" ) String action,
//                              Model model) {
//        if (action.equals("delete")){
//            userService.deleteUser(userId);
//        }
//        return "redirect:/administration";
//    }

}