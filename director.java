package com.company;

import java.util.ArrayList;
import java.util.List;

class director extends person {

    List<movie> movies = new ArrayList<>();
    director(String id, String name) {
        this.id = id;
        this.name = name;

    }
}
