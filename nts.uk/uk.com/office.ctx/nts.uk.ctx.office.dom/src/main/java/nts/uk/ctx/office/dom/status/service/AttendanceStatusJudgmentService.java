package nts.uk.ctx.office.dom.status.service;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
import nts.uk.ctx.office.dom.status.ActivityStatus;

public class AttendanceStatusJudgmentService {

	public Optional<ActivityStatus> getActivityStatus(Required required, String sId,  GeneralDate date) {
		//WorkInfoOfDailyPerformance WorkInformationRepository
		//TimeLeavingOfDailyPerformance
		//WorkType
		return null;
	}
	
	public interface Required {
	
	/* 
	 * @param sid  社員ID
	 * @param date 年月日
	 * @return Optional<社員の外出情報>
	 */
	public Optional<GoOutEmployeeInformation> getGoOutEmployeeInformation(String sid, GeneralDate date);
	
	/* @param sid  社員ID
	 * @param date 年月日>:
	 * @return Optional<ActivityStatus> Optional<在席のステータス>
	 */
	public Optional<ActivityStatus> getActivityStatus(String sid, GeneralDate date);
		
	}
}
