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
public class YearlyHolidayDto {

	/*次回付与日*/
	private GeneralDate nextTime;
	
	/*付与日数*/
	private int grantedDaysNo;
	
	private YearlyHolidayInfo nextTimeInfo;
	
	/*次回付与日*/
	private GeneralDate nextGrantDate;
	
	private YearlyHolidayInfo nextGrantDateInfo;
	
	private YearlyHolidayInfo afterGrantDateInfo;
	
}
