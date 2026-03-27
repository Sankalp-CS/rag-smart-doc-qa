package com.rag.controller;
import java.io.IOException;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
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

        List<Document> docs = vectorStoreService.search(question, fileName);

        StringBuilder contextBuilder = new StringBuilder();

        for (Document doc : docs) {
            contextBuilder.append(doc.getContent()).append("\n");
        }

        String context = contextBuilder.toString();

        String prompt = "You are an AI assistant.\n" +
                "Answer ONLY using the provided context.\n" +
                "If the answer is not in the context, say 'Not found in document'.\n\n" +
                "Context:\n" + context +
                "\n\nQuestion: " + question;

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
//    public void deleteByFileName(String fileName) {
//        try {
//            FilterExpressionBuilder b = new FilterExpressionBuilder();
//            vectorStoreService.delete(List.of(b.eq("fileName", fileName).build().toString()));
//            System.out.println("Deleted existing chunks for: " + fileName);
//        } catch (Exception e) {
//            // If no chunks exist yet, deletion is a no-op — safe to ignore
//            System.out.println("No existing chunks found for: " + fileName + " (skipping delete)");
//        }
//    }
}
