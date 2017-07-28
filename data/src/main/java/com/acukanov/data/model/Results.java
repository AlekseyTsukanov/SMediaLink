package com.acukanov.data.model;


import com.google.gson.annotations.SerializedName;

public class Results {
    private static final String ID = "id";
    private static final String VOTE_COUNT = "vote_count";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String TITLE = "title";
    private static final String POPULARITY = "popularity";
    private static final String POSTER_PATH = "poster_path";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";

    @SerializedName(VOTE_COUNT)
    public int voteCount;
    @SerializedName(ID)
    public int id;
    @SerializedName(VOTE_AVERAGE)
    public double voteAverage;
    @SerializedName(TITLE)
    public String title;
    @SerializedName(POPULARITY)
    public double popularity;
    @SerializedName(POSTER_PATH)
    public String posterPath;
    @SerializedName(BACKDROP_PATH)
    public String backdropPath;
    @SerializedName(OVERVIEW)
    public String overview;
    @SerializedName(RELEASE_DATE)
    public String releaseDate;

    // region getters and setters

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    // endregion

}
