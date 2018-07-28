package com.example.admin.demoapp;

public class ItemData {

    private String firstName;
    private String lastName;

    private int number;

    private String team;

    private String time;

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

    public ItemData(String firstName, String lastName, int number, String team, String time) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.team = team;
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
