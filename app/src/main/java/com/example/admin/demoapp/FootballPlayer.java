package com.example.admin.demoapp;


public class FootballPlayer {
    private String firstName;
    private String lastName;
    private int clothers_number;

    public FootballPlayer(String firstName, String lastName, int clothers_number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.clothers_number = clothers_number;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getClothers_number() {
        return clothers_number;
    }

    public void setClothers_number(int clothers_number) {
        this.clothers_number = clothers_number;
    }
}
