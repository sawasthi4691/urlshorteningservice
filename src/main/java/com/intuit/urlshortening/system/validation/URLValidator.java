package com.intuit.urlshortening.system.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class URLValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLValidator.class);
    private static final String URL_REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    /**
     * Validate Url passed from request.
     * @param url : url
     * @return : Boolean
     */
    public boolean validateURL(String url) {
        LOGGER.info("Inside : URLValidator :  validateURL  url : {}" , url);
        Matcher m = URL_PATTERN.matcher(url);
        return m.matches();
    }
}
