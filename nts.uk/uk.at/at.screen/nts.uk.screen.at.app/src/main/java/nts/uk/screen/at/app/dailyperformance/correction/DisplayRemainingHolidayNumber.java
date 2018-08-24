package nts.uk.screen.at.app.dailyperformance.correction;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.dailyperformance.correction.dto.remainnumber.AnnualLeaveDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.remainnumber.CompensatoryHolidayDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.remainnumber.HolidayRemainNumberDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.remainnumber.ReserveLeaveDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.remainnumber.SubstitutionHolidayDto;

/**
 * 
 * @author HungTT - 休暇残数を表示する
 *
 */

@Stateless
public class DisplayRemainingHolidayNumber {
	
	public HolidayRemainNumberDto getRemainingHolidayNumber(String employeeId) {
		GeneralDate baseDate = GeneralDate.today();
		HolidayRemainNumberDto output = new HolidayRemainNumberDto(); 
		output.setAnnualLeave(new AnnualLeaveDto(true, true, 12, 12));
		output.setReserveLeave(new ReserveLeaveDto(true, 12));
		output.setCompensatoryLeave(new CompensatoryHolidayDto(true, true, 14, 14));
		output.setSubstitutionLeave(new SubstitutionHolidayDto(true, 15));
		return output;		
	}

}
