package com.acukanov.data;


import com.acukanov.data.model.Results;

import java.util.HashMap;
import java.util.List;

import static com.acukanov.data.model.CategoryTypes.POPULAR;
import static com.acukanov.data.model.CategoryTypes.TOP_RATED;

public class Categories {
    private HashMap<Enum, List<Results>> categories;

    public Categories() {
        categories = new HashMap<>(2);
    }

    public void addTopRated(List<Results> results) {
        addCollection(results, TOP_RATED);
    }

    public void addPopular(List<Results> results) {
        addCollection(results, POPULAR);
    }

    public List<Results> getTopRated() {
        return getCollection(TOP_RATED);
    }

    public List<Results> getPopular() {
        return getCollection(POPULAR);
    }

    public void setTopRated(List<Results> results) {
        setCollection(results, TOP_RATED);
    }

    public void setPopular(List<Results> results) {
        setCollection(results, POPULAR);
    }
    // region private methods

    private List<Results> getCollection(Enum category) {
        return categories.get(category);
    }

    private void setCollection(List<Results> results, Enum category) {
        categories.put(category, results);
    }

    private void addCollection(List<Results> results, Enum category) {
        List<Results> saved = categories.get(category);
        if (saved != null) {
            saved.addAll(results);
        } else {
            saved = results;
        }
        categories.put(category, saved);
    }
    // endregion

    // region getters and setters

    public HashMap<Enum, List<Results>> getCategories() {
        return categories;
    }

    private void setCategories(HashMap<Enum, List<Results>> categories) {
        this.categories = categories;
    }
    // endregion
}
