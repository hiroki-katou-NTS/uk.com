package nts.uk.screen.at.app.dailyperformance.correction.dto.remainnumber;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * 
 * @author HungTT
 *
 */

@Data
@NoArgsConstructor
public class HolidayRemainNumberDto {

	private AnnualLeaveDto annualLeave;
	
	private ReserveLeaveDto reserveLeave;
	
	private CompensatoryHolidayDto compensatoryLeave;
	
	private SubstitutionHolidayDto substitutionLeave;
	
}
