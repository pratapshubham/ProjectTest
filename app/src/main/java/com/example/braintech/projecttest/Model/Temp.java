package com.example.braintech.projecttest.Model;

import com.example.braintech.projecttest.DatabaseHandlerClass;

public class Temp {

    public static DatabaseHandlerClass getDatabaseHandler()
    {
        return databaseHandler;
    }

    public static void setDatabaseHandler(DatabaseHandlerClass databaseHandler)
    {
        Temp.databaseHandler = databaseHandler;
    }

    public static DatabaseHandlerClass databaseHandler;
}
