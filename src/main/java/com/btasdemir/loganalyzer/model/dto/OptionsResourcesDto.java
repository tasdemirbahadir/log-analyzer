package com.btasdemir.loganalyzer.model.dto;

import java.util.Date;

public class OptionsResourcesDto {

    private Date startDate;
    private Date endDate;
    private Integer threshold;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }
}
