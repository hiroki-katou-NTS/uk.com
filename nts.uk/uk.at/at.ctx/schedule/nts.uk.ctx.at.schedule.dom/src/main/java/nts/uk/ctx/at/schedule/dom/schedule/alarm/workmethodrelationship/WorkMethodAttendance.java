package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
/**
 * 勤務方法(出勤)
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class WorkMethodAttendance implements WorkMethod {

	//就業時間帯コード
	private final WorkTimeCode workTimeCode; 
	
	@Override
	public WorkMethodClassfication getWorkMethodClassification() {
		return WorkMethodClassfication.ATTENDANCE;
	}

	@Override
	public boolean determineIfApplicable(Require require, WorkInformation workInfo) {
		return this.workTimeCode.equals(workInfo.getWorkTimeCode().v());
	}

}
