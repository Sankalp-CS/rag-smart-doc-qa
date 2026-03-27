package com.rag.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TextChunkService {
			
	public List<String> splitText(String text) {

	    int chunkSize = 1000;
	    int overlap = 200;

	    List<String> chunks = new ArrayList<>();

	    for (int i = 0; i < text.length(); i += (chunkSize - overlap)) {
	        int end = Math.min(i + chunkSize, text.length());
	        chunks.add(text.substring(i, end));
	    }

	    System.out.println("Chunks created: " + chunks.size());

	    return chunks;
	}
}
