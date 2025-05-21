package com.office.calender.organizer.util;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UploadFileService {

	public String upload(String m_id, MultipartFile file) {
		log.info("upload()");
		
		boolean result = false;
		
		String fileOriName = file.getOriginalFilename();
		String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());
		String uploadDir = "c:\\calender\\upload\\" + m_id;
		
		UUID uuid = UUID.randomUUID();
		String uniqueFileName = uuid.toString().replaceAll("-", "");
		
		File saveFile = new File(uploadDir + "\\" + uniqueFileName + fileExtension);
		if(!saveFile.exists())
			saveFile.mkdirs();
		
		try {
			
			file.transferTo(saveFile);
			result = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		if (result) {
			log.info("FILE UPLOAD SUCCESS!!");
			return uniqueFileName + fileExtension;
			
		} else {
			log.info("FILE UPLOAD FAIL!!");
			return null;
			
		}
		
	}
	
}
