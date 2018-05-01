package edu.psu.avp5564.mymovielist.model;

import java.io.Serializable;

/**
 * Created by swegmaster on 4/29/18.
 */

public class Movie implements Serializable {

    private final String posterRequestURL = "https://image.tmdb.org/t/p/w200_and_h300_bestv2";

    private String id;
    private String title;
    private String posterURL;
    private String overview;
    private String releaseDate;
    private String rating;
    private boolean isAdded;

    public Movie() {

    }

    public Movie(String id, String title, String posterURL, String overview, String releaseDate, String rating, boolean isAdded) {
        this.id = id;
        this.title = title;
        this.posterURL = posterRequestURL + posterURL;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.isAdded = isAdded;
    }

    public boolean getIsAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getPosterRequestURL() {
        return posterRequestURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterRequestURL + posterURL;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return convertDate(releaseDate);
//        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
//                "posterRequestURL='" + posterRequestURL + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", posterURL='" + posterURL + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }

    public String convertDate(String releaseDate) {
        if(releaseDate.contains("-"))
        {
            String[] date = releaseDate.split("-");
            String year = date[0];
            String month = date[1];
            String day = date[2];

            month = setMonth(month);

            return month + " " + day + ", " + year;
        } else {
            return releaseDate;
        }
    }

    public String setMonth(String month) {

        int monthNumber = Integer.parseInt(month);

        String monthName;

        switch (monthNumber) {
            case 1:
                monthName = "January";
                break;
            case 2:
                monthName = "February";
                break;
            case 3:
                monthName = "March";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "June";
                break;
            case 7:
                monthName = "July";
                break;
            case 8:
                monthName = "August";
                break;
            case 9:
                monthName = "September";
                break;
            case 10:
                monthName = "October";
                break;
            case 11:
                monthName = "November";
                break;
            case 12:
                monthName = "December";
                break;
            default:
                monthName = "Invalid month";
                break;
        }

        return monthName;
    }
}

