package com.ashim.linkedinClone.uploader_service;

import com.ashim.linkedinClone.uploader_service.service.UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class UploaderController {   // http://localhost:9050/uploads/file

//    private final UploaderService uploaderService;
//
//    @PostMapping
//    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
//        String url = uploaderService.upload(file);
//        return ResponseEntity.ok(url);
//    }
//}
//public class UploaderController {

    private final UploaderService uploaderService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) {
        String url = uploaderService.upload(file);
        return ResponseEntity.ok(url);
    }
}