package com.polarbear.demo6;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoticeController {

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String index() {
        return "redirect:admin";
    }

    @RequestMapping(value = { "/updateMsg" }, method = RequestMethod.POST)
    public String updateMsg(Model model, @RequestParam("systemMessage") String message, RedirectAttributes redirectAttributes) {

        model.addAttribute("message", message);
        redirectAttributes.addFlashAttribute("message", message);
        FileWriter file = null;

        try {
            file = new FileWriter("/data/message.json");
            file.write(message);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:notice";
    }

    @RequestMapping(value = { "/previewMsg" }, method = RequestMethod.POST)
    public String previewMsg(Model model, @RequestParam("systemMessage") String message) {
        model.addAttribute("message", message);
        return "preview";
    }


    @RequestMapping(value = { "/notice" }, method = RequestMethod.GET)
    public String readMessage(Model model) {

        try {
            String content = new String(Files.readAllBytes(Paths.get("/data/message.json")));            
            model.addAttribute("message", content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "notice";
    }

}