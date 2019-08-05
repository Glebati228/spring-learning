package com.example.exampler;

import com.example.exampler.domain.Role;
import com.example.exampler.domain.User;
import com.example.exampler.repositories.UserRepo;
import com.example.exampler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class RegistrationController
{
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration()
    {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @Valid User user,
            BindingResult bindingResult,
            Model model)
    {

        /*if(!userService.isUnicalMail(user.getEmail()))
        {
            model.addAttribute("exist", "This mail exists");
            return "registration";
        }*/

        boolean isConfirm = StringUtils.isEmpty(passwordConfirm);
        if(isConfirm)
        {
            model.addAttribute("password2Error", "Confirm password cannot be empty");
            return "registration";
        }

        if(user.getPassword() != null && !user.getPassword().equals(passwordConfirm))
        {
            model.addAttribute("message", "Password and confrim password not equals!");
            return "registration";
        }

        if(isConfirm || bindingResult.hasErrors())
        {
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );

            Map<String, String> errors = bindingResult.getFieldErrors().stream().collect(collector);

            for(String key : errors.keySet())
            {
                System.out.println(key);
                System.out.println(errors.get(key));
            }

            model.mergeAttributes(errors);
            return "registration";
        }

        if (!userService.AddUser(user)) {
            model.addAttribute("message", "User already exists");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activation/{code}")
    public String activate(
            @PathVariable String code,
            Model model)
    {
        boolean active = userService.isActiveUser(code);

        if(active)
        {
            model.addAttribute("message", "Successfully activation");
        }
        else
            {
                model.addAttribute("message", "Activation failed!");
            }

        return "login";
    }
}
