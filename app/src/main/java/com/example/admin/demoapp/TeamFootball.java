package com.example.admin.demoapp;

import java.util.List;


public class TeamFootball {

    private String name;

    private List<FootballPlayer> list;

    public TeamFootball(String name, List<FootballPlayer> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FootballPlayer> getList() {
        return list;
    }

    public void setList(List<FootballPlayer> list) {
        this.list = list;
    }
}
