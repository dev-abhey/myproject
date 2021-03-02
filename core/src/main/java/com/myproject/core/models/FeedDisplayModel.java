package com.myproject.core.models;

import com.myproject.core.utils.RssPollingUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Model(adaptables = Resource.class)
public class FeedDisplayModel {

    /**
     * injected itemCount
     */
    @Inject
    @Optional
    private String itemCount;

    /**
     * injected feedUrl
     */
    @Inject
    @Optional
    private String feedUrl;

    /**
     * injected authoredItems
     */
    @Optional
    @Inject
    private List<ItemModel> authoredItems;

    /**
     * items list
     */
    private List<ItemModel> items;

    /**
     * fallback feed input URL
     */
    private String defaultFeedUrl = "https://www.nba.com/bucks/rss.xml";

    /**
     * default item count to limit the number of results being displayed on UI
     */
    private int defaultItemCount = 10;

    @PostConstruct
    protected void init(){
        RssPollingUtils rssPollingUtils = RssPollingUtils.getInstance();

        RssModel rssFeed = StringUtils.isNotEmpty(feedUrl) ? rssPollingUtils.pollRssFeedFromURL(feedUrl)
                : rssPollingUtils.pollRssFeedFromURL(defaultFeedUrl);


        items = (null != rssFeed && null != rssFeed.getChannel())? rssFeed.getChannel().getItems() : null;

        if((null == items || items.isEmpty()) && (null != authoredItems && !authoredItems.isEmpty())) {
            items = authoredItems;
            covertDateFormat(items);
        }

        /* sorting the items based on latest pubDate*/
        items = (null != items && !items.isEmpty()) ? sortItems(items) : null;
        defaultItemCount = StringUtils.isNotEmpty(itemCount) ? Integer.parseInt(itemCount) : defaultItemCount;

    }

    /**
     * Method to convert date format for standardisation
     * @param items list
     */
    private void covertDateFormat(List<ItemModel> items){
        DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        DateFormat targetFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");

        List<ItemModel> faultItems = new ArrayList<>();
        for (ItemModel item : items) {
            try {
                item.setPubDate(targetFormat.format(sourceFormat.parse(item.getPubDate())));
            } catch (ParseException e) {
                faultItems.add(item);
            }
        }
        items.removeAll(faultItems);
    }

    /**
     * Sort the @Item list based on pubDate
     * @param items list
     * @return items list
     */
    private List<ItemModel> sortItems(List<ItemModel> items){
        Collections.sort(items, new Comparator<ItemModel>() {
            DateFormat f = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
            @Override
            public int compare(ItemModel c1, ItemModel c2)
            {
                try {
                    return f.parse(c2.getPubDate()).compareTo(f.parse(c1.getPubDate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return items;
    }

    /**
     * Getter for ItemList
     * @return items
     */
    public List<ItemModel> getItems(){
        if(null != items){
            return items.size() >= defaultItemCount ? items.subList(0, defaultItemCount) : items;
        }
        return new ArrayList<>();
    }
}

