package com.myproject.core.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "channel")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChannelModel {

    /**
     * mapped item field
     */
    @XmlElement(name = "item")
    private List<ItemModel> items;

    /**
     * Getter for items
     * @return items
     */
    public List<ItemModel> getItems() {
        return items;
    }

    /**
     * Setter for items
     * @param items
     */
    public void setItems(List<ItemModel> items) {
        this.items = items;
    }
}
