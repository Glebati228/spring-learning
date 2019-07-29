package com.example.exampler;

import com.example.exampler.domain.Message;
import com.example.exampler.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/editMes")
@PreAuthorize("hasAuthority('ADMIN')")
public class EDMessageController
{
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("{message}")
    public String AllMessages(@PathVariable Message message, Model model)
    {
        model.addAttribute("message", message);
        return "editgMessage";
    }

    @PostMapping("gedit")
    public String EditMessage(
            @RequestParam("id") Message message,
            @RequestParam String text,
            @RequestParam String tag) {
        message.SetText(text);
        message.SetTag(tag);
        messageRepo.save(message);
        return "redirect:/main";
    }

    @PostMapping("gdelete")
    public String DeleteMessage(@RequestParam("id") Message message)
    {
        messageRepo.delete(message);
        return "redirect:/main";
    }
}