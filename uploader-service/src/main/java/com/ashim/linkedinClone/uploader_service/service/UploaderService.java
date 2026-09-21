package com.ashim.linkedinClone.uploader_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploaderService {
    String upload(MultipartFile file);

}
