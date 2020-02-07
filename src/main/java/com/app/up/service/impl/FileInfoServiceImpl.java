package com.app.up.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.up.model.FileInfo;
import com.app.up.repository.FileInfoRepository;
import com.app.up.service.IFileInfoService;

@Service
public class FileInfoServiceImpl implements IFileInfoService {
	
	private FileInfoRepository repo;
	
	public FileInfoServiceImpl(FileInfoRepository repo) {
		this.repo = repo;
	}
	@Override
	public FileInfo saveFile(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		Path path = Paths.get("E:\\Files\\"+fileName);
		Path location = Files.write(path, file.getBytes());
		FileInfo fileInfo = FileInfo.builder().fileName(fileName).fileLocation(location.toString()).build();
		return repo.save(fileInfo);
	}
	
	@Override
	public Resource downloadFile(String fileName) throws MalformedURLException {
		FileInfo fileInfo = repo.findByFileName(fileName);	
		return new UrlResource(Paths.get(fileInfo.getFileLocation()).toUri());
	}
	
	@Override
	public List<FileInfo> saveMultipleFiles(List<MultipartFile> files) throws IOException {
		List<FileInfo> fileInfos = files.stream().map(file -> {
			String fileName = file.getOriginalFilename();
			Path path = Paths.get("E:\\Files\\"+fileName);
			Path write = null;
			try {
				write = Files.write(path, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			FileInfo fileInfo = FileInfo.builder().fileName(fileName).fileLocation(write.toString()).build();
			return fileInfo;
		}).collect(Collectors.toList());
		
		return repo.saveAll(fileInfos);
	}
}
