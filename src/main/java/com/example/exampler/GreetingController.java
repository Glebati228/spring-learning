package com.example.exampler;
import com.example.exampler.domain.Message;
import com.example.exampler.domain.User;
import com.example.exampler.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class GreetingController
{
    @Autowired
    private MessageRepo messageRepo;
    @GetMapping("/")
    public String greeting(Map<String, Object> model)
    {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model)
    {
        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }
    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Model model)
    {
        Message message = new Message(text, tag, user);

        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Model model)
    {
        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty())
        {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        model.addAttribute("messages", messages);

        return "main";
    }
}