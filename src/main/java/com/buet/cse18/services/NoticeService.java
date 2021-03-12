package com.buet.cse18.services;

import com.buet.cse18.customExceptions.NoticeNotFoundException;
import com.buet.cse18.entity.Notice;
import com.buet.cse18.repositories.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @PersistenceContext
    EntityManager entityManager;


    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Transactional
    public String createNotice(Notice notice) {
        noticeRepository.save(notice);
        return "Notice created successfully";
    }

    @Transactional
    public String updateNotice(Notice notice) {

        Optional<Notice> noticeOptional = noticeRepository.findById(notice.getNoticeId());

        if(noticeOptional.isPresent())
        {
            Notice notice1 = noticeOptional.get();

            notice1.setNoticeTopic(notice.getNoticeTopic());
            notice1.setNoticeDate(notice.getNoticeDate());
            notice1.setNoticeImageLink(notice.getNoticeImageLink());
            notice1.setNoticeIsMadeBy(notice.getNoticeIsMadeBy());
            notice1.setNoticeAttachmentLink(notice.getNoticeAttachmentLink());
            notice1.setNoticeDescription(notice.getNoticeDescription());

            noticeRepository.save(notice1);

            return "Notice updated successfully";
        }
        else
        {
            log.error("Notice with id: "+notice.getNoticeId()+" does not exists");
            throw new NoticeNotFoundException("Can not find notice with the given Id: "+notice.getNoticeId());
        }
    }

    @Transactional
    public String deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
        return "Notice deleted successfully";
    }

    public List<Notice> getNotices(int pageNumber) {

        int pageSize = 10; //products per page

        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Optional<List<Notice>> optionalNoticeList = noticeRepository.getNotices(pageable);

        if(optionalNoticeList.isPresent())
        {
            return optionalNoticeList.get();
        }
        else
        {
            throw new NoticeNotFoundException("No notices available");
        }
    }

    public List<Notice> searchForNotices(String searchQuery) {

        Query query = entityManager.createNativeQuery("SELECT * FROM notice Where MATCH(notice_topic) AGAINST (:searchQuery IN BOOLEAN MODE)", Notice.class);

        query.setParameter("searchQuery", searchQuery+"*");

        try
        {
            Iterator iterator = query.getResultList().iterator();

            List<Notice> result = new ArrayList<>();

            while (iterator.hasNext()) {
                Notice notice = (Notice) iterator.next();
                result.add(notice);
            }

            return result;

        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
