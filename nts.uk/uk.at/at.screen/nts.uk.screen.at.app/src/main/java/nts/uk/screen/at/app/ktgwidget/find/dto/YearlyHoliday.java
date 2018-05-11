package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class YearlyHoliday {

	/*次回付与日*/
	private GeneralDate nextTime = GeneralDate.today();
	
	/*付与日数*/
	private int grantedDaysNo = 0;
	
	private YearlyHolidayInfo nextTimeInfo = new YearlyHolidayInfo();
	
	/*次回付与日*/
	private GeneralDate nextGrantDate = GeneralDate.today();
	
	private YearlyHolidayInfo nextGrantDateInfo  = new YearlyHolidayInfo();
	
	private YearlyHolidayInfo afterGrantDateInfo = new YearlyHolidayInfo();
	
	/*出勤率*/
	private int attendanceRate = 0;
	
	/*労働日数*/
	private int workingDays = 0;
	
	/* 計算方法 */
	private int calculationMethod = 0;
	
	/*一斉付与を利用する*/
	private int useSimultaneousGrant = 0;
	
}
