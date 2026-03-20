package com.rag.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TextChunkService {
			
	public List<String> splitText(String text)
	{
		int chunksize=500;
		
		List<String> chunks=new ArrayList<>();
		for(int i=0;i<text.length();i+=chunksize)
		{
			 chunks.add(text.substring(i, Math.min(text.length(), i + chunksize)));
		}
		return chunks;
	}
}
