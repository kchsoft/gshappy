package PrayAndBirthday;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;

public class prayANDbirthday {

	private static int[] DaysOfMonth = {31,28,31,30,31,30,31,31,30,31,30,31};

	public static void main(String[] args){
		try {
			Scanner input = new Scanner(System.in);
			String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
			String username = "postgres";
			String password = "vldrjtmxkdlf3#";
			String sql = "";
			String Quarter = "";
			String temp = "";
			String[] BirthdayMD = new String[3];
			Connection connection = DriverManager.getConnection(jdbcURL,username,password);
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE); 
			ResultSet result; //= stmt.executeQuery("select count(*) from one");
			Calendar Cal = Calendar.getInstance();
			LocalDate Date = LocalDate.now();
//			Date = LocalDate.of(2023,4,28);
			int Day = Date.getDayOfMonth();
			int Month = Date.getMonthValue();
			int id = 0;
/*			int WeekDay = getWeekDay(Calendar.getInstance(),Month);
			int Day_Of_Week = Cal.get(Calendar.DAY_OF_WEEK);
			result.next();
			int rowCount = result.getInt(1);
			if(Day_Of_Week == Calendar.SUNDAY || Day_Of_Week == Calendar.SATURDAY) {
				System.out.println("휴무입니다.");
				return;
				}*/
			
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
			birthday.set_BirthdayTable(stmt);
			result = birthday.get_Birthday(stmt);
			
			// BirthDay
			System.out.printf("%d월 %d일 생일자\n",Month,Day);
				while(result.next()) {
			temp = result.getString("birthday");
				BirthdayMD = temp.split("-");
				if(Month == Integer.valueOf(BirthdayMD[1]) && Day == Integer.valueOf(BirthdayMD[2]))
					System.out.printf("%s %s\n" , result.getString("community"),result.getString("name"));
			}
	
			// Pray
			System.out.printf("\n-------------------------------\n\n");
			result = pray.getPray(stmt);
			boolean resultHasValue = result.relative(Day);
			if(!resultHasValue) while(!result.absolute((int)(Math.random()*DaysOfMonth[Month-1]+1)));
			do {
				System.out.printf("-%s %s %s\n<기도제목>\n %s\n\n" , result.getString("community"),
								result.getString("team"),result.getString("name"),result.getString("pray"));
			} while(result.relative(DaysOfMonth[Month-1]));

	
			System.out.printf("-------------------------------\n\n");
			
			// Disciple Birthday
			birthday.set_DiscipleBirthday(stmt);
			result = birthday.get_DiscipleBirthday(stmt);
			System.out.printf("%d월 리더훈련팀 생일자\n",Month);
			while(result.next()) {
					BirthdayMD = result.getString("birthday").split("-");
					int birthdayMonth = Integer.valueOf(BirthdayMD[1]);
					int birthdayDay = Integer.valueOf(BirthdayMD[2]);
					if(Month == birthdayMonth) {
						System.out.printf("%s %s %d일\n",result.getString("community"),result.getString("name"),birthdayDay);
						if(Day == Integer.valueOf(BirthdayMD[2])) System.out.println("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
					}
				}

			System.out.printf("");
			sql = input.nextLine();
			input.close();
			stmt.close();
			connection.close();
			result.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.printf("\nProgram exit\n");
			Scanner input = new Scanner(System.in);
			String t = input.nextLine();
		}
	}

//	private static int getWeekDay(Calendar Cal,int Month) {
//		int WeekDay = 0;
//		Cal.set(Calendar.DATE, 1);
//
//		for(int i = 0 ; i < DaysOfMonth[Month-1] ; i++) {
//			int	day_of_week = Cal.get(Calendar.DAY_OF_WEEK);
//			if(day_of_week == Calendar.SUNDAY || day_of_week == Calendar.SATURDAY){ 
//				Cal.add(Calendar.DATE, 1); 
//				continue;
//			}
//			Cal.add(Calendar.DATE, 1);
//			WeekDay++;
//		}
//		
//		return WeekDay;
//	}
}
