package com.example.demo.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

@Slf4j

public class UploadFile {

    public static String fileSave(String uploadPath, MultipartFile file) throws IOException {
        File uploadPathDir = new File(uploadPath);

        if(!uploadPathDir.exists()) uploadPathDir.mkdir();

        //임의 생성된 파일명
        String generatedId = UUID.randomUUID().toString();

        //파일명과 확장명
        String originalFileName = file.getOriginalFilename();
        String fileExtension = extractFileExtension(originalFileName);
        String saveFileName = generatedId + "." + fileExtension;

        String savePath = generatePath(uploadPath);

        File target = new File(uploadPath + savePath, saveFileName);
        FileCopyUtils.copy(file.getBytes(), target);

        return filePath(uploadPath, savePath, saveFileName);
    }

    private static String filePath(String uploadPath, String path, String fileName) {
        String filePath = uploadPath + path + File.separator + fileName;
        return filePath.substring(uploadPath.length()).replace(File.separator,"/");
    }

    /* 저장경로 생성 */
    private static String generatePath(String uploadPath) {
        Calendar calendar = Calendar.getInstance();
        String yyyy = File.separator + calendar.get(Calendar.YEAR);
        String mm = yyyy + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.MONTH) + 1);
        String dd = mm + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.DATE));
        createDir(uploadPath, yyyy, mm, dd);
        return dd;
    }

    private static void createDir(String uploadPath, String... paths) {
        for(String path : paths) {
            File dirPath = new File(uploadPath + path);
            if(!dirPath.exists()) dirPath.mkdir();
        }
    }




    /*확장자 추출*/
    public static String extractFileExtension(String fileName) {
        int dot = fileName.lastIndexOf(".");
        if (dot != -1 & fileName.length() - 1 > dot) {
            return fileName.substring(dot + 1);
        } else {
            return "";
        }
    }
}
