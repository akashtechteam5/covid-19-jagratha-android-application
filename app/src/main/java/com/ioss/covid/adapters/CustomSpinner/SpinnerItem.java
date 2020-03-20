package com.ioss.covid.adapters.CustomSpinner;

/**
 * Created by ioss on 18/1/18.
 */

public class SpinnerItem {
    String value, id;

    public SpinnerItem(String value, String id) {
        this.value = value;
        this.id = id;
    }

    public String getName() {
        return value;
    }

    public String getId() {
        return id;
    }
}
