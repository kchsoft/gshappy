package PrayAndBirthday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pray{
	String sql;
	String quarter;
	ResultSet result;

	Pray(String q){
		quarter = q;
	}
	
	void setTable(Statement stmt) throws SQLException {
		if(quarter.equals("one")) {//one
			sql = "DROP TABLE IF EXISTS one CASCADE;"
				+ "create table one (Time varchar , community varchar , name varchar ,\n"
				+ "birthday varchar , pray varchar);";
			stmt.executeUpdate(sql);
		}
		else {//other
			sql = "DROP TABLE IF EXISTS "+quarter+" CASCADE;" // reWrite
				+ "create table "+quarter+" (Time varchar , community varchar , name varchar ,\n"
				+ "pary varchar);";
			stmt.executeUpdate(sql);
		}
	}

	void setCSV(Statement stmt) throws SQLException {
		sql = "COPY one FROM 'C:/DataBaseCsvFile/"+quarter+".csv' DELIMITER ',' CSV HEADER;";
		stmt.executeUpdate(sql);
		return;
	}

	ResultSet getPray(Statement stmt) throws SQLException { 
		sql = "select * from one";
		return stmt.executeQuery(sql);
	}
}
