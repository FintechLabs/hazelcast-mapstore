package com.fintechlabs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StatementIterable<T> implements Iterable<T> {

    private PreparedStatement statement;

    public StatementIterable(PreparedStatement statement) {
        this.statement = statement;
    }

    @Override
    public Iterator<T> iterator() {
        try {
            List<String> uniqueIdList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                uniqueIdList.add(resultSet.getString("unique_id"));
            }
            return (Iterator<T>) uniqueIdList.iterator();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
