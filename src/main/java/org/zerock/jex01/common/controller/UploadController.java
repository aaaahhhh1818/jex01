package org.zerock.jex01.common.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.jex01.common.dto.UploadResponseDTO;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Log4j2
public class UploadController {

    @GetMapping("/sample/upload")
    public void uploadGET() {

    }

    @ResponseBody
    @PostMapping("/removeFile")
    public ResponseEntity<String> removeFile(@RequestBody Map<String, String> data) throws Exception {

        // 2021/09/08
        File file = new File("C:\\upload" + File.separator + data.get("fileName"));

        boolean checkImage = Files.probeContentType(file.toPath()).startsWith("image");

        //파일은 삭제됨
        file.delete();

        if(checkImage) {
            File thumbnail = new File(file.getParent(), "s_" + file.getName()); //상위폴더의 이름 가져옴
            log.info(thumbnail);
            thumbnail.delete();
        }
        return ResponseEntity.ok().body("delete");
    }

    @GetMapping("/downFile")
    public ResponseEntity<byte[]> download(@RequestParam("file") String fileName) throws Exception {

        //파일객체 알아오기
        File file = new File("C:\\upload" + File.separator + fileName);

        //다운로드 할 때 원래 이름으로 다운받게 처리
        String originalFileName = fileName.substring(fileName.indexOf("_") + 1);

        HttpHeaders headers = new HttpHeaders();
        //다운로드할 때 mime 타입 다른거 처리
        headers.add("Content-Type", "application/octet-stream");
        //파일 이름으로만 다운로드 되는거 타입 지정해서 다운받을 수 있게.
        headers.add("Content-Disposition", "attachment; filename="
                + new String(originalFileName.getBytes(StandardCharsets.UTF_8),"ISO-8859-1"));
        byte[] data = FileCopyUtils.copyToByteArray(file);

        return ResponseEntity.ok().headers(headers).body(data);

    }

    @GetMapping("/viewFile")
    @ResponseBody
    public ResponseEntity<byte[]> viewFile(@RequestParam("file") String fileName) throws Exception {//파라미터보낼 때는 파일 받을때는 변수 //타입 달라도 쓸 수 있게

        // C:\\upload\\2021/9/08/ㅇㅇ.jsp
        File file = new File("C:\\upload" + File.separator + fileName);

        log.info(file);

        //결과데이터 받아오기
        ResponseEntity<byte[]> result = null;

        //받을 때 바이트 배열로 받아버림
        byte[] data = FileCopyUtils.copyToByteArray(file);

        //mime type 알아보기
        //probeContentType() 타입 뭔지 알아와줌
        String mimeType = Files.probeContentType(file.toPath());

        log.info("mimeType: " + mimeType);

        //ok() -> http 보낼 때 서버 메세지 내 마음대로 보내줄 수 있는거???? 스프링 방식
        //이미지 조회 코드
        result = ResponseEntity.ok().header("Content-Type", mimeType).body(data);

        return result;

    }

    @ResponseBody //response 데이터 JSON으로 처리됨
    @PostMapping("/upload")
    public List<UploadResponseDTO> uploadPost(MultipartFile[] uploadFiles) {//jsp에서 append()에 사용한 이름 파라미터로 줘야함

        log.info("-----------------------------------");

        if (uploadFiles != null && uploadFiles.length > 0) {

            List<UploadResponseDTO> uploadedList = new ArrayList<>(); //객체 담는 생성자

            for (MultipartFile multipartFile : uploadFiles) {
                try {
                    uploadedList.add(uploadProcess(multipartFile));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }//for

            return uploadedList;

        }//end

        return null; //null값으로 담는이유????

    }

    private UploadResponseDTO uploadProcess(MultipartFile multipartFile) throws Exception {

        String uploadPath = "C:\\upload";

        String folderName = makeFolder(uploadPath); // 2021-09-07 //경로

        //log.info(multipartFile.getContentType()); //MIME 타입
        //log.info(multipartFile.getOriginalFilename());
        log.info(multipartFile.getSize());
        log.info("-----------------------------------");

        String fileName = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String originalFileName = fileName;

        //파일이름 중복안되게 만들어주기
        fileName = uuid + "_" + fileName; //uuid

        File savedFile = new File(uploadPath+File.separator+folderName, fileName);

        //실제 upload
        FileCopyUtils.copy(multipartFile.getBytes(), savedFile); // 파일 copy 부분 -> 여기까지 파일 저장까지했음

        //Thumbnail 처리
        String mimeType = multipartFile.getContentType();
        boolean checkImage = mimeType.startsWith("image");

        if(checkImage) { //이미지 여부
            File thumbnailFile = new File(uploadPath+File.separator+folderName, "s_" + fileName); //섬네일 저장 이름
            Thumbnailator.createThumbnail(savedFile, thumbnailFile, 100, 100);
        }

        return UploadResponseDTO.builder()
                .uuid(uuid)
                .uploadPath(folderName.replace(File.separator,"/"))
                .fileName(originalFileName)
                .image(checkImage)
                .build();

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
