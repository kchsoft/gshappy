package Disciple;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class prayANDbirthday {

	public static void main(String[] args) throws SQLException, IOException {
		Scanner input = new Scanner(System.in);
		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
		String username = "postgres";
		String password = "vldrjtmxkdlf3#";
		String sql = "";
		String Quarter = "";
		String temp = "";
		String[] BirthdayMD = new String[3];
		Connection connection = DriverManager.getConnection(jdbcURL,username,password);
		Statement stmt = connection.createStatement(); 
		ResultSet result;
		LocalDate Date = LocalDate.now();
//		Date = LocalDate.of(2023,2,4);
		int Day = Date.getDayOfMonth();
		int Month = Date.getMonthValue();
		int id;
		int[] DaysOfMonth = {31,28,31,30,31,30,31,31,30,31,30,31};
		switch(Month) {
		case 12: case 1: case 2:
			Quarter = "one";
			break;
		case 3: case 4: case 5:
			Quarter = "two";
			break;
		case 6: case 7: case 8:
			Quarter = "three";
			break;
		case 9: case 10: case 11:
			Quarter = "four";
			break;
		}
		Pray pray = new Pray(Quarter);
		pray.setTable(stmt);
		pray.setCSV(stmt);

		Birthday birthday = new Birthday();
		birthday.set_mmddTable(stmt);
		birthday.updateONEbirthday(stmt);
		birthday.set_BirthdayTable(stmt);
		result = birthday.get_Birthday(stmt);

		System.out.printf("%d월 %d일 생일자\n",Month,Day);
		while(result.next()) {
			temp = result.getString("birthday");
			BirthdayMD = temp.split("-");
			if(Month == Integer.valueOf(BirthdayMD[0]) && Day == Integer.valueOf(BirthdayMD[1]))
				System.out.printf("%s %s\n" , result.getString("community"),result.getString("name"));
		}

		System.out.println();
		
		result = pray.getPray(stmt);
		while(result.next()) {
//			id = result.getInt("id") - Day; // ex) if Month == 1 , Day 1 == id 32
			id = result.getRow()- Day;
			if(id == 0 || id % DaysOfMonth[Month-1] == 0) {
//			if(true)
				System.out.printf("공동체: %s\n이름 : %s\n<기도제목>\n%s\n\n" , result.getString("community"),result.getString("name"),
					result.getString("pray").replace('?', '-'));
			}
		}

		birthday.set_DiscipleBirthday(stmt);
		result = birthday.get_DiscipleBirthday(stmt);
		System.out.printf("%d월 리더훈련팀 생일자\n",Month);
		while(result.next()) {
				BirthdayMD = result.getString("birthday").split("-");
				int birthdayMonth = Integer.valueOf(BirthdayMD[1]);
				int birthdayDay = Integer.valueOf(BirthdayMD[2]);
				if(Month == birthdayMonth) {
					System.out.printf("%s %s %d일\n",result.getString("community"),result.getString("name"),birthdayDay);
				}
			}

		System.out.printf("\nProgram exit\n");
		sql = input.nextLine();
		input.close();
		stmt.close();
		connection.close();
		result.close();
	}
}
