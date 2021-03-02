package com.myproject.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "channel")
@XmlAccessorType(XmlAccessType.FIELD)
@Model(adaptables = Resource.class)
public class ItemModel {

    /**
     *
     * injected / mapped title variable
     */
    @Optional
    @Inject
    @XmlElement(name = "title")
    private String title;

    /**
     * injected / mapped description variable
     */
    @Optional
    @Inject
    @XmlElement(name = "description")
    private String description;

    /**
     * injected / mapped publication date variable
     */
    @Optional
    @Inject
    @XmlElement(name = "pubDate")
    private String pubDate;

    /**
     * Getter for title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for pubDate
     * @return pubDate
     */
    public String getPubDate() {
        return pubDate;
    }

    /**
     * Setter for title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter for description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for pubDate
     * @param pubDate
     */
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}

