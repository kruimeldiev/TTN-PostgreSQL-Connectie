package org.example.Database;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostgresService {

    public static void PostgresConnectieTesten() {

        try {
            // Hiermee wordt de driver geladen door het programma
            // Deze stap is niet (meer) verplicht, maar was in Java 1.6 en eerder wel nodig
            Class.forName("org.postgresql.Driver");

            // Aanmaken van de connectie met de database met behulp van de URL, database naam en database password
            Connection conn = DriverManager.getConnection(Constants.CONN_URL, Constants.CONN_USER, Constants.CONN_PASS);

            // Testen of de connectie succesvol is
            if (conn != null) {
                System.out.println("Connectie gemaakt met database");
            } else {
                System.out.println("Error bij connectie maken");
            }

            // Sluiten van de connectie
            conn.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Deze functie voert de waarde van een MQ135 sensor in de 'MQ135ppmWaarde' tabel
    public static void GasModuleWaardeInvoeren(String moduleNaam, Integer waarde) {

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(Constants.CONN_URL, Constants.CONN_USER, Constants.CONN_PASS);

            // Huidige datum van het systeem gebruiken om naar de database door sturen als Timestamp
            Date datum = new Date();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
            String formattedDatum = formatter.format(datum);

            String query = "INSERT INTO \"smartfarm\".\"gasmodule\" (module_naam, module_waarde, module_timestamp) VALUES ('" + moduleNaam + "', " + waarde + ", '" + formattedDatum + "'" + ");";

            Statement stat = conn.createStatement();

            stat.executeUpdate(query);

            conn.close();

            System.out.println("Database insert succesvol");

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Database insert mislukt");
        }
    }

}
