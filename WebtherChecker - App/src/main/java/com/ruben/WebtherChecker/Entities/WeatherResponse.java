package com.ruben.WebtherChecker.Entities;

import java.util.List;

public class WeatherResponse {
    private List<Location> list;

    public List<Location> getList() {
        return list;
    }

    public void setList(List<Location> list) {
        this.list = list;
    }
}
