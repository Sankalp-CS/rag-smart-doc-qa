# 🧠 Smart PDF Q&A System (RAG-based)

A Retrieval-Augmented Generation (RAG) application that allows users to upload PDFs and ask questions based strictly on document content.

---

## 🚀 Features

- 📄 Upload PDF documents
- ✂️ Automatic text extraction & chunking
- 🔎 Semantic search using embeddings
- 🧠 Context-aware question answering
- 📦 Vector storage using PostgreSQL (pgvector)
- 🌐 Simple frontend interface (HTML/CSS/JS)

---

## 🛠️ Tech Stack

- Backend: Spring Boot, Spring AI
- Database: PostgreSQL + pgvector
- LLM: Ollama / Gemini
- Frontend: HTML, CSS, JavaScript
- Build Tool: Maven

---

## ⚙️ How It Works

1. User uploads a PDF  
2. Text is extracted from the document  
3. Text is split into chunks  
4. Each chunk is converted into embeddings  
5. Embeddings are stored in PostgreSQL (pgvector)  
6. User asks a question  
7. Relevant chunks are retrieved  
8. LLM generates answer using retrieved context  

---

## 🧪 API Endpoints

### 📤 Upload PDF

### ❓ Ask Question


Future Improvements
🗑️ File-specific delete functionality
📂 Multi-document querying
🔐 Authentication system
📊 Better UI/UX
⚡ Faster retrieval optimization
