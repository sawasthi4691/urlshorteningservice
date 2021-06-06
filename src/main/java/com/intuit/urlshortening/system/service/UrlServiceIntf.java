package com.intuit.urlshortening.system.service;

import com.intuit.urlshortening.system.exception.ServiceNotFoundException;
import com.intuit.urlshortening.system.model.UrlRequest;

public interface UrlServiceIntf {

    Boolean validateUrl(String url);

    String shortenURL(String appURL , UrlRequest urlRequest);

    String getLongURLFromID(String uniqueID) throws ServiceNotFoundException;
}
