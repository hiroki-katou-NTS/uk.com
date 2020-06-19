package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * 日別勤怠の出退勤
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.出退勤時刻.日別勤怠の出退勤
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class TimeLeavingOfDailyAttd implements DomainObject{
	// 1 ~ 2
	// 出退勤
	private List<TimeLeavingWork> timeLeavingWorks;
	// 勤務回数
	private WorkTimes workTimes;
	public TimeLeavingOfDailyAttd(List<TimeLeavingWork> timeLeavingWorks, WorkTimes workTimes) {
		super();
		this.timeLeavingWorks = timeLeavingWorks;
		this.workTimes = workTimes;
	}
}
