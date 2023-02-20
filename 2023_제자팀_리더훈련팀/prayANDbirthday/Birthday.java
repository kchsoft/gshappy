package PrayAndBirthday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Birthday {
	String sql = "";
	ResultSet result;
	Birthday(){
		
	}


	void set_BirthdayTable(Statement stmt) throws SQLException {
		sql = "DROP TABLE IF EXISTS leaderbirthday CASCADE;\n"
			+ "create table LeaderBirthday (community varchar , name varchar , birthday varchar)";
		stmt.executeUpdate(sql);
		sql = "COPY leaderbirthday FROM 'C:/DataBaseCsvFile/leaderbirthday.csv' DELIMITER ',' CSV HEADER;";
		stmt.executeUpdate(sql);
	}

	ResultSet get_Birthday(Statement stmt) throws SQLException {
		sql = "select * from leaderbirthday;";
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
