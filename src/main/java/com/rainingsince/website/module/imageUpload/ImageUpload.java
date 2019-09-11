package com.rainingsince.website.module.imageUpload;

import com.rainingsince.web.response.ResponseBuilder;
import com.rainingsince.web.utils.IDUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("image/upload")
public class ImageUpload {

    @PostMapping()
    @ResponseBody
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseBuilder.error();
        }
        try {
            return saveFile(file);
        } catch (Exception e) {
            return ResponseBuilder.error();
        }
    }


    private ResponseEntity saveFile(MultipartFile multipartFile) throws IOException {
        String baseUrl = "http://note.caodebo.com/";
        String[] fileAbsolutePath = {};
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream = multipartFile.getInputStream();
        if (inputStream != null) {
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
        try {
            fileAbsolutePath = FastDFSClient.upload(file);
        } catch (Exception e) {
            return ResponseBuilder.error();
        }
        if (fileAbsolutePath == null) {
            return ResponseBuilder.error();
        }
        return ResponseBuilder.ok(new ImageEntity(baseUrl + fileAbsolutePath[0] + "/" + fileAbsolutePath[1]));
    }

}
