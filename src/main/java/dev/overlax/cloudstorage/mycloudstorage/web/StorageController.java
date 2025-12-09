package dev.overlax.cloudstorage.mycloudstorage.web;

import dev.overlax.cloudstorage.mycloudstorage.model.SecurityUser;
import dev.overlax.cloudstorage.mycloudstorage.service.FileService;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StorageController {

    private final FileService fileService;

    @GetMapping
    public String storage(Model model, @AuthenticationPrincipal SecurityUser user) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        List<String> files = fileService.getFileNamesList(user.getUsername());
        model.addAttribute("files", files);

        return "index";
    }

    @PostMapping
    public String upload(Model model,
                         @RequestParam("file") MultipartFile file,
                         @AuthenticationPrincipal SecurityUser user) throws FileUploadException {

        if (!file.isEmpty()) {
            fileService.upload(file, user.getUsername());
            model.addAttribute("file", file.getOriginalFilename());
        }

        return "redirect:/";
    }

    @PostMapping("/new")
    public String addFolder(Model model,
                            @RequestParam("folderName") String folderName,
                            @AuthenticationPrincipal SecurityUser user) {

        if (!folderName.isEmpty()) {
            fileService.addFolder(user.getUsername(), folderName);
        }

        return "redirect:/";
    }

    @DeleteMapping
    public String delete(@RequestParam String filename,
                         @AuthenticationPrincipal SecurityUser user) {

        fileService.deleteFile(user.getUsername(), filename);
        return "redirect:/";
    }
}
