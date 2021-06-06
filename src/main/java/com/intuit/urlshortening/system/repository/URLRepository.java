package com.intuit.urlshortening.system.repository;

import com.intuit.urlshortening.system.model.URLDto;
import com.intuit.urlshortening.system.model.UrlRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

import static com.intuit.urlshortening.system.util.URLConstant.DEAFULT_TIME;

@Repository
public class URLRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLRepository.class);

    @Autowired
    private RedisTemplate<String, URLDto> redisTemplate;


    public void saveUrl(URLDto urlDto, UrlRequest urlRequest) {
        LOGGER.info("Inside URLRepository : saveUrl : start");
        if(StringUtils.isNotEmpty(urlRequest.getTimeLimit())) {
            LOGGER.info("Saving: {} at {}", urlDto.getUrl(), urlDto.getId());
            if(StringUtils.isNotEmpty(urlRequest.getCustomUrl())) {
                urlDto.setId(urlRequest.getCustomUrl());
                redisTemplate.opsForValue().set(urlRequest.getCustomUrl(), urlDto, Long.parseLong(urlRequest.getTimeLimit()), TimeUnit.HOURS);
            }else{
                redisTemplate.opsForValue().set(urlDto.getId(), urlDto, Long.parseLong(urlRequest.getTimeLimit()), TimeUnit.HOURS);
            }
        }else{
            if(StringUtils.isNotEmpty(urlRequest.getCustomUrl())) {
                urlDto.setId(urlRequest.getCustomUrl());
                redisTemplate.opsForValue().set(urlRequest.getCustomUrl(), urlDto, DEAFULT_TIME, TimeUnit.HOURS);
            }else{
                //default is 6 hours
                redisTemplate.opsForValue().set(urlDto.getId(), urlDto, DEAFULT_TIME, TimeUnit.HOURS);
            }
        }
        LOGGER.info("Inside URLRepository : saveUrl : end");
    }

    public URLDto getUniqueID(String uniqueID){
       return redisTemplate.opsForValue().get(uniqueID);
    }
}