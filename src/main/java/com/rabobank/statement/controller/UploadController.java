package com.rabobank.statement.controller;

import com.rabobank.statement.service.CustStmtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;

/*
 * Main controller to upload and validate the MT940 file
*/
@Controller
public class UploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private CustStmtService custStmtService;

    @GetMapping("/")
    public String index() {
        LOGGER.info("Inside the index method - redirecting to upload html");
        return "upload";
    }

    @PostMapping("/upload")
    public String custStmtUpload(HttpSession session, @RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes) {
        LOGGER.info("Inside the statement upload method");
        if (file.isEmpty()) {
            LOGGER.error("File name is empty");
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        if( file.getOriginalFilename().endsWith("csv") ||
                file.getOriginalFilename().endsWith("xml")) {
            session.setAttribute("custStmtList",custStmtService.validateCustStmt(file));
        }
        else {
            LOGGER.error("Invalid file type uploaded");
            redirectAttributes.addFlashAttribute("message",
                    "Invalid file type uploaded '" + file.getOriginalFilename() + "'");
        }
        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        LOGGER.info("Inside the statement upload status method");
        return "uploadStatus";
    }
}