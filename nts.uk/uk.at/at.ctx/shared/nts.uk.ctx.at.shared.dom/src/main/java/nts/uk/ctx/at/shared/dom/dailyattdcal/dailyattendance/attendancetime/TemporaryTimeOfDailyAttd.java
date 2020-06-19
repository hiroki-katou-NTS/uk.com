package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * 日別勤怠の臨時出退勤
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.出退勤時刻.日別勤怠の臨時出退勤
 * @author tutk
 *
 */
@Getter
public class TemporaryTimeOfDailyAttd implements DomainObject {
	
	//勤務回数
	private WorkTimes workTimes;
	
	// 1 ~ 3
	//出退勤
	private List<TimeLeavingWork> timeLeavingWorks;

	public TemporaryTimeOfDailyAttd(WorkTimes workTimes, List<TimeLeavingWork> timeLeavingWorks) {
		super();
		this.workTimes = workTimes;
		this.timeLeavingWorks = timeLeavingWorks;
	}

}
