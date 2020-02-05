package com.app.up.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.up.model.FileInfo;
import com.app.up.service.IFileInfoService;

@RestController
@RequestMapping(FIleInfoController.URL)
public class FIleInfoController {
	
	public static final String URL = "/api/v1/fileInfo";
	
	private IFileInfoService infoService;

	public FIleInfoController(IFileInfoService infoService) {
		this.infoService = infoService;
	}
	
	@PostMapping
	public ResponseEntity<FileInfo> save(@RequestParam("file")MultipartFile file) throws IOException {
		FileInfo savedFile = infoService.saveFile(file);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedFile.getId()).toUri();
		return ResponseEntity.created(location).body(savedFile);
	}
	
	@GetMapping("/{fileName}")
	public ResponseEntity<Resource> download(@PathVariable("fileName")String fileName) throws MalformedURLException {
		Resource resource = infoService.downloadFile(fileName);
		String filename2 = resource.getFilename();
		System.out.println(filename2);
		return ResponseEntity.ok()
							 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" +filename2)
							 .contentType(MediaType.parseMediaType("application/octet-stream"))
							 .body(resource);
	}
	
	@GetMapping(value = "/num1/{num1}/num2/{num2}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> test(@PathVariable("num1")int num1,@PathVariable("num2")int num2) {
		List<String> res = new ArrayList<>();
		for(int i = num1;i<=num2;i++) {
			if(i%3 == 0) {
				res.add("Fizz ");
			}
			else if(i%5 == 0) {
				res.add("Buzz ");
			}
			else {
				res.add(i+" ");
			}
		}
		return res;
	}
}