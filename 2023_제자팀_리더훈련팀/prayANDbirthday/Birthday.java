package Disciple;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Birthday {
	String sql = "";
	ResultSet result;
	Birthday(){
		
	}

	void set_mmddTable(Statement stmt) throws SQLException {
		sql = "DROP TABLE IF EXISTS mmdd CASCADE;\n"
			+ "create table mmdd (time varchar , birthdaymmdd varchar);";
		stmt.executeUpdate(sql);
	}

	void updateONEbirthday(Statement stmt) throws SQLException {
		sql = "insert into mmdd select time , SUBSTRING(birthday,6,5) as monthday from one;\n";
		stmt.executeUpdate(sql);
		sql = "update one set birthday = mmdd.birthdaymmdd\n"
			+ "from mmdd\n"
			+ "where one.time = mmdd.time;";
		stmt.executeUpdate(sql);
	}

	void set_BirthdayTable(Statement stmt) throws SQLException {
		sql = "DROP TABLE IF EXISTS birthday CASCADE;\n"
			+ "create table Birthday as select community , name , birthday from one order by birthday;";
		stmt.executeUpdate(sql);
	}

	ResultSet get_Birthday(Statement stmt) throws SQLException {
		sql = "select * from birthday;";
		return stmt.executeQuery(sql);
	}

	void set_DiscipleBirthday(Statement stmt) throws SQLException {
		sql = "DROP TABLE IF EXISTS disciplebirthday CASCADE;\n"
			+ "create table disciplebirthday (community varchar , name varchar , birthday varchar);";
		stmt.executeUpdate(sql);
		sql = "COPY disciplebirthday FROM 'C:/DataBaseCsvFile/disciplebirthday.csv' DELIMITER ',' CSV HEADER;";
		stmt.executeUpdate(sql);
	}	

	ResultSet get_DiscipleBirthday(Statement stmt) throws SQLException {
		sql = "select * from disciplebirthday order by birthday;";
		return stmt.executeQuery(sql);
	}
	
}
