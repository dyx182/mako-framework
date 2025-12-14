package com.github.dyx182.parser;

import com.github.dyx182.model.TestCase;
import com.github.dyx182.model.TestStep;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserYaml {

    private final Yaml yaml = new Yaml();

    public TestCase parseTest(String inputFile) {

        try (InputStream inputStream = new FileInputStream(inputFile)) {
            Map<String, Object> rawData = yaml.load(inputStream);

            TestCase testCase = new TestCase();
            testCase.setName((String) rawData.get("name"));

            List<TestStep> steps = new ArrayList<>();
            Object stepsObject = rawData.get("steps");

            if (stepsObject instanceof List) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> stepsList = (List<Map<String, Object>>) stepsObject;

                for (Map<String, Object> stepMap : stepsList) {
                    TestStep step = new TestStep();
                    step.setAction((String) stepMap.get("action"));

                    Map<String, Object> parameters = new HashMap<>(stepMap);
                    parameters.remove("action");
                    step.setParams(parameters);

                    steps.add(step);
                }
            }
            testCase.setSteps(steps);
            return testCase;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse test: " + inputFile, e);
        }
    }
}
