package com.app.up.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.app.up.model.FileInfo;

public interface IFileInfoService {
	
	public FileInfo saveFile(MultipartFile file) throws IOException;
	
	public List<FileInfo> saveMultipleFiles(List<MultipartFile> files) throws IOException;
	
	public Resource downloadFile(String fileName) throws MalformedURLException;
	
}
