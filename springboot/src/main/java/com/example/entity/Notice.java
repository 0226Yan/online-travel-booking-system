package com.example.entity;

import javax.persistence.*;

@Table(name = "notice")
public class Notice {
    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增
    @Column(name = "noticeId")
    private Integer noticeId;
    @Column(name = "noticeName")
    private String noticeName;
    @Column(name = "content")
    private String content;
    @Column(name = "time")
    private String time;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

