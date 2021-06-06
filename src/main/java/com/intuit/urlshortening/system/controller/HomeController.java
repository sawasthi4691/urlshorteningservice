package com.intuit.urlshortening.system.controller;


import com.intuit.urlshortening.system.exception.ServiceNotFoundException;
import com.intuit.urlshortening.system.exception.ValidationException;
import com.intuit.urlshortening.system.model.UrlRequest;
import com.intuit.urlshortening.system.service.UrlServiceIntf;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

import static com.intuit.urlshortening.system.util.URLConstant.*;

@RestController
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    private UrlServiceIntf urlServiceIntf;

    @Autowired
    public HomeController(UrlServiceIntf urlServiceIntf) {
        this.urlServiceIntf = urlServiceIntf;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createShortURL(@RequestBody @Valid final UrlRequest urlRequest, HttpServletRequest request)  {
        LOGGER.info("Received url to shorten: {}" , urlRequest.getUrl());
        String shortenedUrl;
        String originalUrl = urlRequest.getUrl();
        if(StringUtils.isEmpty(originalUrl)){
            throw new ValidationException(EMPTY_URL);
        }
        if (Boolean.TRUE.equals(urlServiceIntf.validateUrl(originalUrl))) {
            String localURL = request.getRequestURL().toString();
            shortenedUrl = urlServiceIntf.shortenURL(localURL,urlRequest);
            LOGGER.info("Shortened url to: {} " , shortenedUrl);
        }else {
            throw new ValidationException(NOT_VALID_URL);
        }
        return ResponseEntity.ok(shortenedUrl);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUrl(@PathVariable final String id)  {
        String originalURL = urlServiceIntf.getLongURLFromID(id);
        if(!StringUtils.contains(originalURL, HTTP)){
            originalURL = "http://" + originalURL;
        }
        if (StringUtils.isEmpty(originalURL)) {
            throw new ServiceNotFoundException("No Such key exists");
        } else {
            LOGGER.info("URL retrieved = {}", originalURL);
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalURL))
                .build();
    }

}
