 package com.rendezvous.model;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;

 import java.util.List;

 @Controller
public class UserController {
    @Autowired private UserService service;

    @GetMapping("/users")
     public String showUserList(Model model){
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers",listUsers);
        return "users";
    }

    @GetMapping("/users/new")
     public String showNewForm(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("pageTitle","Ajouter nouveau rendez-vous");
        return "userForm";
    }

    @PostMapping("/users/save")
     private String saveUser(User user){
        service.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
     public String showEditForm(@PathVariable("id") Integer id, Model model ){
        try {
            User user = service.get(id);
            model.addAttribute("user",user);
            model.addAttribute("pageTitle","Modifier rendez-vous");
            return "userForm";
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return "redirect:/users";
        }
    }

     @GetMapping("/users/delete/{id}")
     public String deleteUser(@PathVariable("id") Integer id){
         try {
             service.delete(id);
         } catch (UserNotFoundException e) {
             e.printStackTrace();

         }
         return "redirect:/users";
     }
}
