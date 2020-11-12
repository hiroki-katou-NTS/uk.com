package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 予定時間帯
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.勤務予定.予定時間帯
 */
@Getter
@NoArgsConstructor
public class ScheduleTimeSheet extends DomainObject{
	
	private WorkNo workNo;
	@Setter
	private TimeWithDayAttr attendance;
	@Setter
	private TimeWithDayAttr leaveWork;

	public ScheduleTimeSheet(Integer workNo, int attendance, int leaveWork) {
		super();
		this.workNo = new WorkNo(workNo);
		this.attendance = new TimeWithDayAttr(attendance);
		this.leaveWork = new TimeWithDayAttr(leaveWork);
	}
	
}
