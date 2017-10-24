package nts.uk.ctx.at.record.dom.worktime;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;

/**
 * 
 * @author nampt
 * 日別実績の出退勤 - root
 *
 */
@Getter
public class TimeLeavingOfDailyPerformance extends AggregateRoot {
	
	private String employeeId;
	
	private WorkTimes workTimes;
	
	// 1 ~ 2
	private List<TimeLeavingWork> timeLeavingWorks;
	
	private GeneralDate ymd;

}
