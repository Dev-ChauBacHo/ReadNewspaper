package com.vinhtran.readNewspaper.Model;

public class NewsFeed {
    private String title;
    private String pubDate;
    private String link;
    private String imgUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "newsFeed{" +
                "title='" + title + '\n' +
                ", pubDate='" + pubDate + '\n' +
                ", link='" + link + '\n' +
                ", imgUrl='" + imgUrl + '\n' +
                '}';
    }
}
