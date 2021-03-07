package com.buet.cse18.controllers;

import com.buet.cse18.entity.Notice;
import com.buet.cse18.services.NoticeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(path = "/viewer/noticeBoard/v1")
public class NoticeControlViewer {

    private final NoticeService noticeService;

    public NoticeControlViewer(NoticeService noticeService) {
        this.noticeService = noticeService;
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
}
