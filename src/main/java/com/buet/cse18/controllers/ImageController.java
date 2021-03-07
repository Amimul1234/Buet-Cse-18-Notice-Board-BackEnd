package com.buet.cse18.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@RestController
public class ImageController {
    @PostMapping("/imageController/{directory}")

    public String saveImageInProject(@PathVariable("directory") String directory,
                                             @RequestPart(name = "multipartFile") MultipartFile multipartFile)
    {
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
            String failed = "Failed to save image, Please try again";
            return failed;
        }

    }

    @GetMapping(value = "/getImageFromServer", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity sendImageToClient(@RequestParam(name = "path_of_image") String path_of_image)
    {

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

    @DeleteMapping("/getImageFromServer")
    public ResponseEntity deleteImage(@RequestParam(name = "path_of_image") String path_of_image)
    {
        File file = new File(path_of_image);

        if(file.exists())
        {
            try
            {
                file.delete();
                return ResponseEntity.status(HttpStatus.OK).body("Image Successfully Deleted");
            }
            catch (Exception e)
            {
                log.error("Failed to delete image");
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Failed To Delete Image");
            }
        }
        else
        {
            log.error("File does not exists, path is: "+path_of_image);
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Image does not exists");
        }
    }

}
