package com.limeesodaa.healthindex.database;

public class Application {

    public static void main(
            String[] args)
            throws Exception {

        DatabaseInitializer initializer
                = new DatabaseInitializer();

        initializer.initialize();

        System.out.println(
                "Database initialized.");
    }
}
