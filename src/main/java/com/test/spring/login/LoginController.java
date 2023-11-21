package com.test.spring.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
    
    @GetMapping("/submit")
    public String submit() {
        return "submit";
    }
    
    
    @PostMapping("/submit")
    public String submitForm(@ModelAttribute Student student, @RequestParam("file") MultipartFile file, Model model) {
        // Access yourFormObject properties here
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Year: " + student.getYear());
        System.out.println("Subject: " + student.getSubject());
        System.out.println("Term: " + student.getTerm());

        MultipartFile file1 = student.getFile();
        // Handle file logic as needed
        System.out.println( file1.getContentType());
        System.out.println(file1.getName());
        
        List<List<String>> excelData = new ArrayList<>();
        if (!file1.isEmpty()) {
            try (InputStream inputStream = file1.getInputStream()) {
                Workbook workbook = new XSSFWorkbook(inputStream);

                // Assuming the Excel sheet is in the first (0-based) position
                Sheet sheet = workbook.getSheetAt(0);

                // Loop through rows and cells to extract Excel content
                for (Row row : sheet) {
                    List<String> rowData = new ArrayList<>();
                    for (Cell cell : row) {
						rowData.add(cell.toString());
                    }
                    excelData.add(rowData);
                }

                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Pass the Excel data to the Thymeleaf template
        model.addAttribute("excelData", excelData);

        // Your logic for handling the POST request

        return "submit";
    }
}

