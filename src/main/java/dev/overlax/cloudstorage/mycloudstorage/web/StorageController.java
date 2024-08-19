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

import java.util.List;

@Controller
@RequestMapping("/storage")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StorageController {

    private final FileService fileService;

    @GetMapping
    public String storage(Model model, @AuthenticationPrincipal SecurityUser user) {

        List<String> files = fileService.getFilesList(user.getUsername());
        model.addAttribute("files", files);

        return "index";
    }

    @PostMapping
    public String upload(Model model,
                         @RequestParam("file") MultipartFile file,
                         @AuthenticationPrincipal SecurityUser user) throws FileUploadException {

        fileService.upload(file, user.getUsername());
        model.addAttribute("file", file.getOriginalFilename());
        return "index";
    }

    @PostMapping("/new")
    public String addFolder(Model model,
                            @RequestParam("folderName") String folderName,
                            @AuthenticationPrincipal SecurityUser user) {

        fileService.addFolder(user.getUsername(), folderName);

        return "redirect:/storage";
    }
}
