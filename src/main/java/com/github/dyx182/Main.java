package com.github.dyx182;

import com.github.dyx182.runner.TestRunner;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            return;
        }

        String testFile = args[0];
        String locatorsFile = args[1];

        try {
            TestRunner runner = new TestRunner();
            runner.runTest(testFile, locatorsFile);
            System.out.println("test sucsess");
        } catch (Exception e) {
            System.err.println("error" + e.getMessage());
            System.exit(1);
        }
    }
}
