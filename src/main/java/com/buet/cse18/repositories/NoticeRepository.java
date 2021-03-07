package com.buet.cse18.repositories;

import com.buet.cse18.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query("SELECT e from Notice e")
    Optional<List<Notice>> getNotices(Pageable pageable);
}
