package nts.uk.ctx.at.record.dom.worktime;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.worktime.primitivevalue.WorkTimes;

/**
 * 
 * @author nampt 日別実績の臨時出退勤 - root
 *
 */
@Getter
public class TemporaryTimeOfDailyPerformance extends AggregateRoot {

	private String employeeId;

	private WorkTimes workTimes;
	
	// 1 ~ 3
	private List<TimeLeavingWork> timeLeavingWorks;

	private GeneralDate ymd;

	public TemporaryTimeOfDailyPerformance(String employeeId, WorkTimes workTimes,
			List<TimeLeavingWork> timeLeavingWorks, GeneralDate ymd) {
		super();
		this.employeeId = employeeId;
		this.workTimes = workTimes;
		this.timeLeavingWorks = timeLeavingWorks;
		this.ymd = ymd;
	}

}
