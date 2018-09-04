package com.btasdemir.loganalyzer.model;

import java.util.Date;

public class LogEntry {

    private Date date;
    private String ip;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
