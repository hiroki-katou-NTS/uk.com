package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
/**
 * 勤務方法(出勤)
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class WorkingMethodAttendance implements WorkingMethod {

	//就業時間帯コード
	private final WorkTimeCode workTimeCode; 
	
	@Override
	public WorkTypeClassification getWorkTypeClassification() {
		return WorkTypeClassification.Attendance;
	}

	@Override
	public boolean determineIfApplicable(Require require, WorkInformation workInfor) {
		return this.workTimeCode.equals(workInfor.getWorkTimeCode().v());
	}

}
