package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author HungTT
 *
 */

@Data
@NoArgsConstructor
public class HolidayRemainNumberDto {

	private YearHolidaySettingDto annualLeave;
	
	private ReserveLeaveDto reserveLeave;
	
	private CompensLeaveComDto compensatoryLeave;
	
	private SubstVacationDto substitutionLeave;
	
	private Com60HVacationDto com60HVacation;
	
	private GeneralDate nextGrantDate;
	
}
