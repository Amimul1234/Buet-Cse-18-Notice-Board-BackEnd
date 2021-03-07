package com.buet.cse18.controllers;

import com.buet.cse18.entity.Notice;
import com.buet.cse18.services.NoticeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/noticeBoard/v1")
public class NoticeControlAdmin {

    private final NoticeService noticeService;

    public NoticeControlAdmin(NoticeService noticeService) {
        this.noticeService = noticeService;
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
}
