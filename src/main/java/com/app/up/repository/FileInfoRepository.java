package com.app.up.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.up.model.FileInfo;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
	
	public FileInfo findByFileName(String fileName);
}
