package com.fintechlabs;

import com.hazelcast.core.MapStore;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class PersonMapStore implements MapStore<String, Person> {

    private Connection con;
    private PreparedStatement allKeysStatement;

    public PersonMapStore() {
        System.out.println("*******************************         Creating JDBC Connection for Hazelcast Person MapStore    *******************************");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost:3306/webhook_loader?useUnicode=true&characterEncoding=UTF-8&user=root&password=";
            con = DriverManager.getConnection(connectionUrl);
            allKeysStatement = con.prepareStatement("select unique_id from person_domain");
        } catch (ClassNotFoundException | SQLException nfe) {
            nfe.printStackTrace();
        }
    }

    public synchronized void delete(String key) {
        System.out.println("*******************************         Deleting Key => " + key + "   from SQL Database     *******************************");
        try {
            con.createStatement().executeUpdate(format("delete from person_domain where unique_id = '%s'", key));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void store(String key, Person person) {
        System.out.println("*******************************         Storing Key => " + key + " into SQL Database     *******************************");
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT into person_domain(version, date_created, email_address, first_name, last_name, last_updated, phone_number, unique_id) VALUE(?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, 0);
            Timestamp dateCreated = new Timestamp(person.getDateCreated().getTime());
            preparedStatement.setTimestamp(2, dateCreated);
            preparedStatement.setString(3, person.getEmailAddress());
            preparedStatement.setString(4, person.getFirstName());
            preparedStatement.setString(5, person.getLastName());
            Timestamp lastUpdated = new Timestamp(person.getLastUpdated().getTime());
            preparedStatement.setTimestamp(6, lastUpdated);
            preparedStatement.setString(7, person.getPhoneNumber());
            preparedStatement.setString(8, person.getUniqueId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void storeAll(Map<String, Person> map) {
        System.out.println("*******************************         Storing All Keys from SQL Database     *******************************");
        for (Map.Entry<String, Person> entry : map.entrySet()) {
            store(entry.getKey(), entry.getValue());
        }
    }

    public synchronized void deleteAll(Collection<String> keys) {
        System.out.println("*******************************         Deleting All Keys from SQL Database     *******************************");
        for (String key : keys) {
            delete(key);
        }
    }

    public synchronized Person load(String key) {
        System.out.println("*******************************     Loading single key  => " + key + "  from SQL Database    *******************************");
        try {
            try (ResultSet resultSet = con.createStatement().executeQuery(format("select * from person_domain where unique_id ='%s'", key))) {
                if (!resultSet.next()) {
                    return null;
                }
                return new Person(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Map<String, Person> loadAll(Collection<String> keys) {
        Map<String, Person> result = new HashMap<>();
        for (String key : keys) {
            System.out.println("******************************      Loading Key " + key + " into hazelCast          ******************************");
            result.put(key, load(key));
        }
        return result;
    }

    public Iterable<String> loadAllKeys() {
        System.out.println("*******************************     Loading All Keys from SQL Database      *******************************");
        return new StatementIterable<>(allKeysStatement);
    }

}
