package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

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
public class TimeLeavingOfDailyAttdExport implements DomainObject{
	// 1 ~ 2
	// 出退勤
	private List<TimeLeavingWorkExport> timeLeavingWorks;
	// 勤務回数  WorkTimes (1-5)
	private int workTimes;
	public TimeLeavingOfDailyAttdExport(List<TimeLeavingWorkExport> timeLeavingWorks, int workTimes) {
		super();
		this.timeLeavingWorks = timeLeavingWorks;
		this.workTimes = workTimes;
	}
	
}
