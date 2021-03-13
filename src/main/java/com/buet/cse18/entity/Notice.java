package com.buet.cse18.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "notice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Notice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;
    @Column(nullable = false, columnDefinition = "NTEXT")
    private String noticeTopic;
    @Column(nullable = false, columnDefinition = "NTEXT")
    private String noticeDescription;
    @Column(nullable = false)
    private String noticeDate;
    @Column(columnDefinition = "NTEXT")
    private String noticeImageLink;
    @Column(columnDefinition = "NTEXT")
    private String noticeAttachmentLink;
    @Column(nullable = false)
    private String noticeIsMadeBy;
}
