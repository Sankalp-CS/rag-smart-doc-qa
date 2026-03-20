package com.rag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VectorStoreService {

    @Autowired
    private VectorStore vectorStore;

    // ✅ Store chunks
    public void store(List<String> chunks, String fileName) {

        List<Document> docs = chunks.stream()
                .map(chunk -> {
                    Document doc = new Document(chunk);
                    doc.getMetadata().put("fileName", fileName);
                    return doc;
                })
                .toList();

        vectorStore.add(docs);
    }

    // ✅ Search similar chunks (IMPROVED)
    public List<Document> search(String query, String fileName) {

        List<Document> results = vectorStore.similaritySearch(query);

        return results.stream()
                .filter(doc -> fileName.equals(doc.getMetadata().get("fileName")))
                .toList();
    }
}