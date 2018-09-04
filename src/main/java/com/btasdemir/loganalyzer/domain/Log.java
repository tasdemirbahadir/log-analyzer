package com.btasdemir.loganalyzer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "log")
@SequenceGenerator(name = "seq_log", sequenceName = "seq_log")
public class Log {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_log")
    private Long id;

    @Column(name = "ip")
    private String ip;

    @Column(name = "date")
    private Date date;

    @Column(name = "request")
    private String request;

    @Column(name = "cause_to_block")
    private String causeToBlock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getCauseToBlock() {
        return causeToBlock;
    }

    public void setCauseToBlock(String causeToBlock) {
        this.causeToBlock = causeToBlock;
    }
}
