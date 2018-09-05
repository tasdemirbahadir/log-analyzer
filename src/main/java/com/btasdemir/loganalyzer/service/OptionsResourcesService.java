package com.btasdemir.loganalyzer.service;

import com.btasdemir.loganalyzer.model.dto.OptionsResourcesDto;
import com.btasdemir.loganalyzer.resources.OptionsResources;
import org.springframework.stereotype.Component;

@Component
public class OptionsResourcesService {

    public String getAccessLog() {
        return OptionsResources.getAccessLog();
    }

    public OptionsResourcesDto getOptionsResourcesDto() {
        OptionsResourcesDto optionsResourcesDto = new OptionsResourcesDto();
        optionsResourcesDto.setStartDate(OptionsResources.getStartDate());
        optionsResourcesDto.setEndDate(OptionsResources.getEndDate());
        optionsResourcesDto.setThreshold(OptionsResources.getThreshold());
        return optionsResourcesDto;
    }

}
