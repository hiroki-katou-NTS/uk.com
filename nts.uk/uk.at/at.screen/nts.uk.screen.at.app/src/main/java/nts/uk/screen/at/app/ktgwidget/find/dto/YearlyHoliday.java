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
	private GeneralDate nextTime;
	
	/*付与日数*/
	private Double grantedDaysNo = 0.0;
	
	private YearlyHolidayInfo nextTimeInfo = new YearlyHolidayInfo();
	
	/*次回付与日*/
	private GeneralDate nextGrantDate;
	/*付与前*/
	private YearlyHolidayInfo nextGrantDateInfo  = new YearlyHolidayInfo();
	/*付与後*/
	private YearlyHolidayInfo afterGrantDateInfo = new YearlyHolidayInfo();
	
	/*出勤率*/
	private Double attendanceRate = 0.0;
	
	/*労働日数*/
	private Double workingDays = 0.0;
	
	/* 計算方法 */
	private int calculationMethod = 0;
	
	/*一斉付与を利用する*/
	private int useSimultaneousGrant = 0;
	
	private boolean showGrantDate = false;
	
}
