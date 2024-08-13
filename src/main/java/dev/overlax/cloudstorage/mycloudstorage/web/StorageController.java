package dev.overlax.cloudstorage.mycloudstorage.web;

import dev.overlax.cloudstorage.mycloudstorage.model.SecurityUser;
import dev.overlax.cloudstorage.mycloudstorage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/storage")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StorageController {

    private final FileService fileService;

    @GetMapping
    public String storage(Model model, @AuthenticationPrincipal SecurityUser securityUser) {
        System.out.println(securityUser.getUsername());
        return "index";
    }

    @PostMapping
    public String upload(Model model, @RequestParam("file") MultipartFile file) throws FileUploadException {
        fileService.upload(file);
        model.addAttribute("file", file.getOriginalFilename());
        return "index";
    }
}
