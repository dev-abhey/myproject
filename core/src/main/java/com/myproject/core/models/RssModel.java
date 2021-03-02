package com.myproject.core.models;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)
public class RssModel implements Serializable {

    /**
     * Mapped channel field
     */
    @XmlElement(name = "channel")
    private ChannelModel channel;

    /**
     * Getter for channel
     * @return  channel
     */
    public ChannelModel getChannel() {
        return channel;
    }

    /**
     * Setter for channel
     * @param channel
     */
    public void setChannel(ChannelModel channel) {
        this.channel = channel;
    }

}