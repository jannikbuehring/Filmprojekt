package com.company;

import java.util.ArrayList;
import java.util.List;

public class actor extends person{

    List<movie> movies = new ArrayList<>();
    actor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
