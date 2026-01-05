package com.github.dyx182;

import com.github.dyx182.runner.TestRunner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[] {
                    "test-data/tests/simple-test.yml",  // ← ИЗМЕНИЛ ПУТЬ
                    "test-data/pages/"
            };
            System.out.println("⚡ Using test arguments");
        }
//        if (args.length < 1) {
//            System.err.println("Usage: java -jar mako.jar <test-file.yml> [pages-folder]");
//            System.err.println("  Default pages folder: 'pages/'");
//            System.err.println("");
//            System.err.println("Example:");
//            System.err.println("  java -jar mako.jar tests/login.yml");
//            System.err.println("  java -jar mako.jar tests/checkout.yml my_pages/");
//            return;
//        }

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
