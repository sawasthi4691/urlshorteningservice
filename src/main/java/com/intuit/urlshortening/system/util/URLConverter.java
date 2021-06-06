package com.intuit.urlshortening.system.util;

import com.google.common.hash.Hashing;
import com.intuit.urlshortening.system.model.URLDto;
import com.intuit.urlshortening.system.model.UrlRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class URLConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLConverter.class);

    public  URLDto create(final UrlRequest urlRequest) {
        LOGGER.info("Inside URLConverter : create : url {}" , urlRequest.getUrl());
        final String id = Hashing.murmur3_32().hashString(urlRequest.getUrl(), StandardCharsets.UTF_8).toString();
        if(StringUtils.isNotEmpty(urlRequest.getTimeLimit())){
            LocalDateTime localDateTime = LocalDateTime.now();
            localDateTime.plusHours(Long.valueOf(urlRequest.getTimeLimit()));
            return new URLDto(id, urlRequest.getUrl(), localDateTime);
        }
        return new URLDto(id, urlRequest.getUrl(), LocalDateTime.now());
    }


}
