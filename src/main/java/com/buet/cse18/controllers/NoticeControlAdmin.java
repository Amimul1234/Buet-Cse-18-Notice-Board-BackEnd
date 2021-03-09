package com.buet.cse18.controllers;

import com.buet.cse18.entity.Notice;
import com.buet.cse18.services.ImageControllingService;
import com.buet.cse18.services.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(path = "/admin/noticeBoard/v1")
public class NoticeControlAdmin {
    private final NoticeService noticeService;
    private final ImageControllingService imageControllingService;

    public NoticeControlAdmin(NoticeService noticeService, ImageControllingService imageControllingService) {
        this.noticeService = noticeService;
        this.imageControllingService = imageControllingService;
    }

    @PostMapping(path = "/createNewNotice")
    public String createNewNotice(@RequestBody Notice notice)
    {
        return noticeService.createNotice(notice);
    }

    @PutMapping(path = "/updateExistentNotice")
    public String updateExistentNotice(@RequestBody Notice notice)
    {
        return noticeService.updateNotice(notice);
    }

    @DeleteMapping(path = "/deleteExistentNotice")
    public String deleteExistentNotice(@RequestParam(name = "noticeId") Long noticeId)
    {
        return noticeService.deleteNotice(noticeId);
    }

    @GetMapping(path = "/getCurrentNotices")
    public List<Notice> getNotices(@RequestParam("pageNumber") int pageNumber)
    {
        return noticeService.getNotices(pageNumber);
    }

    @GetMapping(path = "/searchNotices")
    public List<Notice> searchForNotices(@RequestParam("searchQuery") String searchQuery)
    {
        return noticeService.searchForNotices(searchQuery);
    }

    @PostMapping("/imageController/{directory}")
    public String saveImageInProject(@PathVariable("directory") String directory,
                                     @RequestPart(name = "multipartFile") MultipartFile multipartFile)
    {
        return imageControllingService.saveImageInFolder(directory, multipartFile);
    }

    @DeleteMapping("/getImageFromServer")
    public String deleteImage(@RequestParam(name = "path_of_image") String path_of_image)
    {
        return imageControllingService.deleteImage(path_of_image);
    }

    @GetMapping(value = "/getImageFromServer", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> sendImageToClient(@RequestParam(name = "path_of_image") String path_of_image)
    {
        return imageControllingService.getImageFromServer(path_of_image);
    }

}
