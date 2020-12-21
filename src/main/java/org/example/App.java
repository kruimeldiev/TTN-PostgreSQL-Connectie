package org.example;

import org.example.Database.PostgresService;
import org.example.Database.TheThingsNetworkService;

public class App {

    public static void main( String[] args ) throws Exception {
        PostgresService.PostgresConnectieTesten();
        TheThingsNetworkService.getDataFromTTN();
    }
}
