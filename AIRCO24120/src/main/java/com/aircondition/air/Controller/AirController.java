package com.aircondition.air.Controller;

import com.aircondition.air.Model.AirCon;
import com.aircondition.air.Repository.AirRepository;
import com.aircondition.air.Service.AirService;
import com.aircondition.air.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class AirController {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private AirRepository airRepository;

    @Autowired
    AirService airService;

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/view"}, method = RequestMethod.GET)
    public String showClaimsList(Model model) {
        System.out.println("I am inside the view controller");
        List<AirCon> viewclient = airService.listAll();
        model.addAttribute("viewclient", viewclient);
        return "admin/view-client";
    }

    @GetMapping("/query")
    public String showQueries(Model model) {
        List<AirCon> viewclient = airRepository.findAll();
        model.addAttribute("viewclient", viewclient);
        return "admin/client-queries-reply";
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam String email, @RequestParam String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Meeting Request");
        mailMessage.setText(message);
        emailSender.send(mailMessage);
        return "admin/dashboard";
    }
//    @GetMapping("/view/delete_claim/{id}")
//    public String deleteclaim(@PathVariable("id") Long id){
//        userService.deleteClaim(id);
//        return "redirect:/gun";
//}

    @RequestMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Optional<AirCon> meeting = airService.findClientById(id);
        byte[] imageBytes = meeting.get().getEquipmentPhoto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }


    @RequestMapping(value = {"/client"}, method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("refrigeration", new AirCon());
        System.out.println("I am inside client form");
        return "client-claim";
    }



//    @PostMapping("/clientform")
//    public String submitForm(@Valid AirCon refrigeration, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "client-claim";
//        }
//
//        airRepository.save(refrigeration);
//
//        model.addAttribute("successMessage", "Form submitted successfully!");
//        return "homepage";
//    }


    @PostMapping("/clientform")
    public String submitForm(@ModelAttribute("refrigeration") @Valid AirCon refrigeration,
                             BindingResult bindingResult,
                             @RequestParam(value = "file", required = false) MultipartFile equipmentPhoto) throws IOException {
        if (bindingResult.hasErrors()) {
            System.out.println("Binding result has errors");
        }

        if (!equipmentPhoto.isEmpty()) {
            String contentType = equipmentPhoto.getContentType();
            if (contentType.equals("image/jpeg") || contentType.equals("image/png")) {
                byte[] imageBytes = equipmentPhoto.getBytes();
                refrigeration.setEquipmentPhoto(imageBytes);
            } else {
                bindingResult.rejectValue("equipmentPhoto", "error.equipmentPhoto", "Invalid file type");
                return "homepage";
            }
        }

        airRepository.save(refrigeration);

        return "homepage";
    }






}



