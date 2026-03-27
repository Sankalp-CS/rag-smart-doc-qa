package com.rag.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VectorStoreService {

    @Autowired
    private VectorStore vectorStore;
    
    

    // 🔹 Store with metadata (fileName)
    public void store(List<String> chunks, String fileName) {

        List<Document> documents = chunks.stream()
                .map(chunk -> {
                    Document doc = new Document(chunk);
                    doc.getMetadata().put("fileName", fileName); // ✅ IMPORTANT
                    return doc;
                })
                .toList();

        vectorStore.add(documents);

        System.out.println("Stored " + documents.size() + " chunks for file: " + fileName);
    }

    // 🔹 Search with file filter
    public List<Document> search(String query, String fileName) {

        List<Document> results = vectorStore.similaritySearch(
                SearchRequest.query(query)
                        .withTopK(5)
                        .withFilterExpression("fileName == '" + fileName + "'")
        );

        // Debug
        System.out.println("\n===== CONTEXT =====");
        results.forEach(doc -> System.out.println(doc.getContent()));
        System.out.println("===================\n");

        return results;
    }

}