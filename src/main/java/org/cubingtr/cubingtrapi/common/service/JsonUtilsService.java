package org.cubingtr.cubingtrapi.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class JsonUtilsService {

	private final ObjectMapper objectMapper;

	public String objectToJsonDocument(Object document) {
		return objectToJsonDocument(document, false);
	}

	public String objectToJsonDocument(Object document, boolean prettyPrint) {
		String jsonString;

		try {
			if (prettyPrint) {
				jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(document);
			} else {
				jsonString = objectMapper.writeValueAsString(document);
			}
		} catch (JsonProcessingException jpe) {
			throw new RuntimeException(jpe);
		}

		return jsonString;
	}

	public <T> T jsonDocumentToObject(String jsonDocument, Class<T> clazz) {
		try {
			return objectMapper.readValue(jsonDocument, clazz);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public <T> T jsonDocumentToObject(String jsonDocument, TypeReference<T> type) {
		try {
			return objectMapper.readValue(jsonDocument, type);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public String readJsonFileContent(String resourcePath) {
		Resource resource = new FileSystemResource(resourcePath);
		try {
			InputStream inputStream = resource.getInputStream();
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			String data = new String(bdata, StandardCharsets.UTF_8);
			return data;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public String readJsonClassPathContent(String resourcePath) {
		Resource resource = new ClassPathResource(resourcePath);
		try {
			InputStream inputStream = resource.getInputStream();
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			String data = new String(bdata, StandardCharsets.UTF_8);
			return data;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
