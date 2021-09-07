package org.zerock.jex01.common.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Controller
@Log4j2
public class UploadController {

    @GetMapping("/sample/upload")
    public void uploadGET() {

    }

    @PostMapping("/upload")
    public void uploadPost(MultipartFile[] uploadFiles) {//jsp에서 append()에 사용한 이름 파라미터로 줘야함

        log.info("-----------------------------------");

        if (uploadFiles != null && uploadFiles.length > 0) {
            for (MultipartFile multipartFile : uploadFiles) {
                try {
                    uploadProcess(multipartFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void uploadProcess(MultipartFile multipartFile) throws Exception {

        String uploadPath = "C:\\upload";

        String folderName = makeFolder(uploadPath); // 2021-09-07

        log.info(multipartFile.getContentType()); //MIME 타입
        log.info(multipartFile.getOriginalFilename());
        log.info(multipartFile.getSize());
        log.info("-----------------------------------");

        String fileName = multipartFile.getOriginalFilename();

        //파일이름 중복안되게 만들어주기
        fileName = UUID.randomUUID().toString() + "_" + fileName;

        File savedFile = new File(uploadPath+File.separator+folderName, fileName);

        //실제 upload
        FileCopyUtils.copy(multipartFile.getBytes(), savedFile); // 파일 copy 부분 -> 여기까지 파일 저장까지했음

        //Thumbnail 처리
        String mimeType = multipartFile.getContentType();

        if(mimeType.startsWith("image")) {
            File thumbnailFile = new File(uploadPath+File.separator+folderName, "s_" + fileName); //섬네일 저장 이름
            Thumbnailator.createThumbnail(savedFile, thumbnailFile, 100, 100);
        }

    }

    // 파일 업로드 할 때 오늘 날짜 폴더 생성해서 파일 저장
    private String makeFolder(String uploadPath) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = simpleDateFormat.format(date); //2021-09-07
        String folderName = str.replace("-", File.separator); //win \\ 를 바꿔주는 역할
        File uploadFolder = new File(uploadPath, folderName); //java에서는 폴더도 파일로 취급

         if(uploadFolder.exists() == false) {
             uploadFolder.mkdirs();
         }

         //폴더에 저장하기 위해 폴더이름으로 리턴
        return folderName;

    }
}
