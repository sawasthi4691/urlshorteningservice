package com.intuit.urlshortening.system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class UrlRequest {

    private String url;
    private String timeLimit;
    private String customUrl;

    @JsonCreator
    public UrlRequest() {

    }

    @JsonCreator
    public UrlRequest(@JsonProperty(value = "url") String url,
                      @JsonProperty(value = "timeLimit") String timeLimit,
                      @JsonProperty(value = "customUrl") String  customUrl) {
        this.url = url;
        this.timeLimit = timeLimit;
        this.customUrl = customUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }
}
