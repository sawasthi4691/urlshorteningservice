package com.intuit.urlshortening.system.model;

import java.time.LocalDateTime;

public class URLDto {

    private  String id;
    private  String url;
    private  LocalDateTime created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public URLDto(String id, String url, LocalDateTime created) {
        this.id = id;
        this.url = url;
        this.created = created;
    }


}
