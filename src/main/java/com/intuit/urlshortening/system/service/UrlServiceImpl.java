package com.intuit.urlshortening.system.service;

import com.intuit.urlshortening.system.exception.ServiceNotFoundException;
import com.intuit.urlshortening.system.model.URLDto;
import com.intuit.urlshortening.system.model.UrlRequest;
import com.intuit.urlshortening.system.repository.URLRepository;
import com.intuit.urlshortening.system.util.URLConverter;
import com.intuit.urlshortening.system.validation.URLValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UrlServiceImpl implements UrlServiceIntf {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlServiceImpl.class);

    private URLValidator urlValidator;

    private URLConverter urlConverter;

    private URLRepository urlRepository;

    @Autowired
    public UrlServiceImpl(URLValidator urlValidator, URLConverter urlConverter, URLRepository urlRepository) {
        this.urlValidator = urlValidator;
        this.urlConverter = urlConverter;
        this.urlRepository = urlRepository;
    }

    /**
     * Call urlValidator to validate the URL.
     *
     * @param url : url
     * @return : Boolean
     */
    @Override
    public Boolean validateUrl(String url) {
        LOGGER.info("Inside : UrlServiceImpl : Start");
        Boolean result = urlValidator.validateURL(url);
        LOGGER.info("Inside : UrlServiceImpl : End");
        return result;
    }

    /**
     * This Function returns Short URL.
     * @param appURL     : appURL
     * @param urlRequest : urlRequest
     * @return
     */
    @Override
    public String shortenURL(String appURL, UrlRequest urlRequest) {
        LOGGER.info("Shortening {}", appURL);
        final URLDto urlDto = urlConverter.create(urlRequest);
        LOGGER.info("URL id generated = {}", urlDto.getId());
        urlRepository.saveUrl(urlDto,urlRequest);
        String baseString = formatLocalURLFromShortener(appURL);
        String shortUrl = baseString + urlDto.getId();
        return shortUrl;

    }


    @Override
    public String getLongURLFromID(String uniqueID) {
        LOGGER.info("Retrieving at {}", uniqueID);
        URLDto urlDto = urlRepository.getUniqueID(uniqueID);
        if (Objects.isNull(urlDto)) {
            throw new ServiceNotFoundException("URL at key" + urlDto + " does not exist");
        }
        LOGGER.info("Converting shortened URL back to {}", urlDto.getUrl());
        return urlDto.getUrl();

    }

    /**
     * Making the LocalURL
     * @param localURL : localURL
     * @return : String
     */
    private String formatLocalURLFromShortener(String localURL) {
        String[] addressComponents = localURL.split("/");
        // remove the endpoint (last index)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addressComponents.length - 1; ++i) {
            sb.append(addressComponents[i]);
            sb.append('/');
        }
        return sb.toString();
    }
}
