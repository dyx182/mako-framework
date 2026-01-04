package com.github.dyx182;

import com.github.dyx182.runner.TestRunner;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            //todo add exception
            return;
        }

        String testFile = args[0];
        String pagesFolder = args.length > 1 ? args[1] : "pages";

        TestRunner runner = new TestRunner();

        try {
            runner.runTest(testFile, pagesFolder);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
