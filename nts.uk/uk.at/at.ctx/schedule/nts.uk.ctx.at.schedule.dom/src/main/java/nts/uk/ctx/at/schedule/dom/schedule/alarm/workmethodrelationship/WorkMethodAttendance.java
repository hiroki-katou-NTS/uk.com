package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
/**
 * 勤務方法(出勤)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.勤務方法の関係性.勤務方法(出勤)
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class WorkMethodAttendance implements WorkMethod {

	/** 就業時間帯コード */
	private final WorkTimeCode workTimeCode; 
	
	@Override
	public WorkMethodClassfication getWorkMethodClassification() {
		return WorkMethodClassfication.ATTENDANCE;
	}

	@Override
	public boolean includes(Require require, WorkInformation workInfo) {
		if (!workInfo.getWorkTimeCodeNotNull().isPresent()) {
			return false;
		}
		return this.workTimeCode.equals(workInfo.getWorkTimeCode());
	}

}
