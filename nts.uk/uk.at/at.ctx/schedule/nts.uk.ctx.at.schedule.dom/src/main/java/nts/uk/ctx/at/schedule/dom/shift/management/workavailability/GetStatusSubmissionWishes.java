package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望.勤務希望の提出状況を取得する
 * @author dungbn
 *
 */
public class GetStatusSubmissionWishes {
	
	public static DesiredSubmissionStatus get(Require require, String cid, String empId, GeneralDate availabilityDate) {
		
		// 1: get(社員ID, 年月日): Optional<一日分の勤務希望>
		Optional<WorkAvailabilityOfOneDay> workAvailabilityOfOneDay = require.get(empId, availabilityDate);
		
		if (!workAvailabilityOfOneDay.isPresent()) {
			return DesiredSubmissionStatus.NO_HOPE;
		}
		
		// 2: [一日分の勤務希望.isPresent]: 休日の勤務希望である(require): boolean
		// 3: return
		return workAvailabilityOfOneDay.get().isHolidayAvailability(require, cid) ? DesiredSubmissionStatus.HOLIDAY_HOPE : DesiredSubmissionStatus.COMMUTING_HOPE; 
	}
	
	
	public static interface Require extends WorkAvailabilityOfOneDay.Require {
		
		//[R-1] 一日分の勤務希望を取得する
		Optional<WorkAvailabilityOfOneDay> get(String employeeID, GeneralDate availabilityDate);
	}
}
