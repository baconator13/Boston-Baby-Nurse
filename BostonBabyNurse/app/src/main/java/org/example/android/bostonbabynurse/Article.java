package org.example.android.bostonbabynurse;

/**
 * Created by alexanderarsenault on 8/25/15.
 */
public class Article {

    private String title;
    private String link;

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

    public String toString() {
        return "Title: " + this.title + "\n" +
                "Link: " + this.link + "\n";
    }

}
