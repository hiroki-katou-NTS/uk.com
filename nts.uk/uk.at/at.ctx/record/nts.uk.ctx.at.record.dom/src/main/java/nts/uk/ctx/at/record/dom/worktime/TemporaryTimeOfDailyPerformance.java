package nts.uk.ctx.at.record.dom.worktime;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;

/**
 * 
 * @author nampt 日別実績の臨時出退勤 - root
 *
 */
@Getter
public class TemporaryTimeOfDailyPerformance extends AggregateRoot {

	//社員ID
	private String employeeId;
	//年月日
	private GeneralDate ymd;
	//出退勤
	private TemporaryTimeOfDailyAttd attendance;
	
	public TemporaryTimeOfDailyPerformance(String employeeId,
			List<TimeLeavingWork> timeLeavingWorks, GeneralDate ymd) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.attendance = new TemporaryTimeOfDailyAttd(timeLeavingWorks);
	}

	public TemporaryTimeOfDailyPerformance(String employeeId, GeneralDate ymd, TemporaryTimeOfDailyAttd attendance) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.attendance = attendance;
	}

}
