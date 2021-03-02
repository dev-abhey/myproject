package com.myproject.core.utils;

import com.myproject.core.models.RssModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Singleton Utility class. It has methods to retrieve RSS feed from given URI.
 */
public class RssPollingUtils {

    // static reference of current class
    private static RssPollingUtils rssPollingUtils = null;

    // Logger
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // JAXB instance
    private JAXBContext jaxbContext;

    // JAXB Unmarshaller
    private Unmarshaller unmarshaller;

    // private constructor
    private RssPollingUtils(){}

    // static method to give the instance of this class
    public static RssPollingUtils getInstance(){
        return rssPollingUtils == null ? new RssPollingUtils() : rssPollingUtils;
    }

    /**
     * Method to poll given URL to fetch feed.
     * @param responseURL
     * @return @RssModel object
     */
    public RssModel pollRssFeedFromURL(String responseURL) {

        InputStreamReader inputStreamReader = null;
        StringBuilder builder = new StringBuilder();
        RssModel rssObject = null;

        try {
            URL url = new URL(responseURL);
            URLConnection urlConnection = url.openConnection();

            if (urlConnection != null) {
                urlConnection.setReadTimeout(10 * 1000);
            }

            if (urlConnection != null && urlConnection.getInputStream() != null) {

                inputStreamReader = new InputStreamReader(urlConnection.getInputStream(), Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                int eof;
                while ((eof = bufferedReader.read()) != -1) {
                    builder.append((char) eof);
                }
                bufferedReader.close();
            }
            if (null != inputStreamReader){
                inputStreamReader.close();
            }

            String xmlResponse = builder.toString();

            jaxbContext = JAXBContext.newInstance(RssModel.class);

            unmarshaller = jaxbContext.createUnmarshaller();

            rssObject = (RssModel) unmarshaller.unmarshal(new StringReader(xmlResponse));

        } catch (JAXBException e) {
            log.error("JAXBException while parsing the RSS feed from the source");
            log.info(e.getMessage(), e);
        } catch (NullPointerException e) {
            log.error("NullPointerException while parsing the RSS feed from the source");
            log.info(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return rssObject;
    }
}
