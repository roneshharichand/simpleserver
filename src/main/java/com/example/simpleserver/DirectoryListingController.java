package com.example.simpleserver;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class DirectoryListingController {

	private static final int PAGE_SIZE = 100;

	private final FreeMarkerConfigurationFactory freeMarkerConfigurationFactory;

	public DirectoryListingController(FreeMarkerConfigurationFactory freeMarkerConfigurationFactory) {
		this.freeMarkerConfigurationFactory = freeMarkerConfigurationFactory;
	}

	@GetMapping("/directory")
	public ResponseEntity<String> getDirectoryListing(@RequestParam String path, @RequestParam(defaultValue = "0") int page) {
		File directory = new File(path);
		if (!directory.exists() || !directory.isDirectory()) {
			return ResponseEntity.badRequest().body("Invalid directory path: " + path);
		}

		File[] fileList = directory.listFiles();
		if (fileList == null) {
			return ResponseEntity.badRequest().body("Error listing directory contents");
		}

		int totalFiles = fileList.length;
		int totalPages = (int) Math.ceil((double) totalFiles / PAGE_SIZE);

		if (page < 0 || page >= totalPages) {
			return ResponseEntity.badRequest().body("Invalid page number: " + page);
		}

		int startIndex = page * PAGE_SIZE;
		int endIndex = Math.min(startIndex + PAGE_SIZE, totalFiles);

		List<File> files = Arrays.asList(fileList).subList(startIndex, endIndex);

		Map<String, Object> model = new HashMap<>();
		model.put("path", path);
		model.put("fileList", files);
		model.put("currentPage", page);
		model.put("hasPreviousPage", page > 0);
		model.put("hasNextPage", page < totalPages - 1);
		model.put("dateFormat", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));

		try {
			Configuration freeMarkerConfig = freeMarkerConfigurationFactory.createConfiguration();
			Template template = freeMarkerConfig.getTemplate("directory_listing.ftl");
			StringWriter writer = new StringWriter();
			template.process(model, writer);
			return ResponseEntity.ok(writer.toString());
		} catch (IOException | TemplateException e) {
			return ResponseEntity.badRequest().body("Error generating directory listing: " + e.getMessage());
		}
	}

	@GetMapping("/actuator/health")
	public ResponseEntity<String> health() {
		return ResponseEntity.ok("Application is healthy");
	}
}
