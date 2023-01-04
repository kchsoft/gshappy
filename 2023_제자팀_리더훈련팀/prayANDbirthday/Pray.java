package Disciple;

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

//		To order time correctly ,concat '9' to 1 month
		sql = "update one set time = '9' || time where substring(time,1,2) = '1-';";
		stmt.executeUpdate(sql);
		return;
	}

	ResultSet getPray(Statement stmt) throws SQLException { 
		// result.getrow() -> we can get id , but this SQL which is complex to gain the "id" is written for study.
//		sql = "select *, row_number() over (order by (select 1)) as id from (select * from "+quarter+" order by time) one";
		sql = "select * from one order by time";
		return stmt.executeQuery(sql);
	}
}
