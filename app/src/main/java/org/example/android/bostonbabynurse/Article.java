package org.example.android.bostonbabynurse;



public class Article {

    private String title;
    private String pubDate;
    private String link;
    private String description;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getContent() {
        return content;
    }

    public void setPubDate(String date) {
        this.pubDate = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return this.title + "\n" +
                "Link: " + this.link + "\n";
    }

}
