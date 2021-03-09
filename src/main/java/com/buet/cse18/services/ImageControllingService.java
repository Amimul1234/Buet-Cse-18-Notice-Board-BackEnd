package com.buet.cse18.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Slf4j
public class ImageControllingService {

    public String saveImageInFolder(String directory, MultipartFile multipartFile) {

        String filename = UUID.randomUUID().toString() + multipartFile.getOriginalFilename();

        try {

            File dir = new File("images" + "/" + directory);

            if(!dir.exists())//Make Directory if not exists
                dir.mkdirs();

            Files.copy(multipartFile.getInputStream(), Paths.get(dir+ "/"+ filename),
                    StandardCopyOption.REPLACE_EXISTING);

            String response = "/getImageFromServer?path_of_image=images/" + directory + "/" + filename;

            multipartFile.getInputStream().close();

            return response;

        } catch (IOException e) {
            log.error("Failed to save image, Error is: "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String deleteImage(String path_of_image) {

        File file = new File(path_of_image);

        if(file.exists())
        {
            try
            {
                file.delete();
                return "Image Successfully Deleted";
            }
            catch (Exception e)
            {
                log.error("Failed to delete image, Error is: "+e.getMessage());
                throw new RuntimeException(e);
            }
        }
        else
        {
            log.error("File does not exists, path is: "+path_of_image);
            throw new RuntimeException("Image file does not exists");
        }
    }

    public ResponseEntity<?> getImageFromServer(String path_of_image) {

        byte[] requested_image;

        try {
            File file = new File(path_of_image);//This is for determining byte array size
            requested_image = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file.getPath());
            fileInputStream.read(requested_image, 0, requested_image.length);
            fileInputStream.close();


            return ResponseEntity
                    .ok()
                    .contentLength(requested_image.length)
                    .body(requested_image);

        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.FAILED_DEPENDENCY)
                    .body("No such image");
        }
    }
}
