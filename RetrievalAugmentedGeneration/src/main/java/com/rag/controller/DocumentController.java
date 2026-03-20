package com.rag.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.rag.service.PdfService;
import com.rag.service.TextChunkService;
import com.rag.service.VectorStoreService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api")
public class DocumentController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private TextChunkService chunkService;

    @Autowired
    private VectorStoreService vectorStoreService;

    @PostMapping("/upload")
    public String uploadPdf(@RequestParam("file") MultipartFile file) throws IOException {

        String text = pdfService.extractFile(file);
        List<String> chunks = chunkService.splitText(text);

        vectorStoreService.store(chunks, file.getOriginalFilename());

        return "Uploaded successfully";
    }

    @Autowired
    private ChatClient chatClient;

    @PostMapping("/ask")
    public String askQuestion(@RequestParam String fileName,
                              @RequestBody String question) {

        var docs = vectorStoreService.search(question, fileName);

        String context = docs.stream()
                .map(doc -> doc.getContent())
                .reduce("", (a, b) -> a + "\n" + b);

        String prompt = "Use the context to answer.\n\n" +
                context +
                "\n\nQuestion: " + question;

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}