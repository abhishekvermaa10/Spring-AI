package com.abhishekvermaa10.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.abhishekvermaa10.advisor.LoggingAdvisor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author abhishekvermaa10
 */
@Slf4j
@Configuration
public class ChatConfig {

	@Bean
	ChatClient chatClient(ChatClient.Builder chatClientBuilder, LoggingAdvisor loggingAdvisor,
			QuestionAnswerAdvisor questionAnswerAdvisor) {
		return chatClientBuilder
				.defaultAdvisors(loggingAdvisor)
				.defaultAdvisors(questionAnswerAdvisor)
				.build();
	}
	
	@Bean
	QuestionAnswerAdvisor questionAnswerAdvisor(VectorStore vectorStore, SearchRequest searchRequest) {
		return QuestionAnswerAdvisor.builder(vectorStore)
				.searchRequest(searchRequest)
				.build();
	}
	
	@Bean
	VectorStore vectorStore(EmbeddingModel embeddingModel) {
		SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel)
			.build();
		File outputFile = new File("src/main/resources/output-data/vectorstore.json");
		if (outputFile.exists()) {
			log.info("Loading existing vector store file: {}", outputFile.getAbsolutePath());
			vectorStore.load(outputFile);
			return vectorStore;
		}
		File inputFolder = new File("src/main/resources/input-data");
		if(!inputFolder.exists()) {
			log.warn("Input folder does not exist: {}", inputFolder.getAbsolutePath());
			return vectorStore;
		}
		List<Document> documents = new ArrayList<>();
		for (File file : inputFolder.listFiles()) {
			if (file.isFile()) {
				log.info("Reading file: {}", file.getAbsolutePath());
				Resource resource = new FileSystemResource(file);
				TikaDocumentReader reader = new TikaDocumentReader(resource);
				List<Document> rawDocuments = reader.get();
				List<Document> chunks = new TokenTextSplitter().split(rawDocuments);
				documents.addAll(chunks);
			}
		}
		vectorStore.add(documents);
		vectorStore.save(outputFile);
		log.info("Vector store saved to file: {}", outputFile.getAbsolutePath());
		return vectorStore;
	}
	
	@Bean
	SearchRequest searchRequest() {
		return SearchRequest.builder()
				.similarityThreshold(0.5)
				.topK(2)
				.build();
	}

}
