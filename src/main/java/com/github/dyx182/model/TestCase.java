package com.github.dyx182.model;

import lombok.Data;

import java.util.List;

@Data
public class TestCase {
    private String name;
    private List<TestStep> steps;
}
