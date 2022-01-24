package com.aws.learn;

public class InputModel {
    private String days;

    public InputModel(String  days) {
        this.days = days;
    }

    public InputModel() {
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
