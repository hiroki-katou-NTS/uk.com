package nts.uk.ctx.at.record.dom.breakorgoout;

import java.util.List;

import lombok.Getter;
//import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;

/**
 * 
 * @author nampt
 * 日別実績の外出時間帯 - root
 *
 */
@Getter
public class OutingTimeOfDailyPerformance extends AggregateRoot {
	//社員ID
	private String employeeId;
	//年月日
	private GeneralDate ymd;
	//時間帯
	private OutingTimeOfDailyAttd outingTime;
	

	public OutingTimeOfDailyPerformance(String employeeId, GeneralDate ymd, List<OutingTimeSheet> outingTimeSheets) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.outingTime = new OutingTimeOfDailyAttd(outingTimeSheets);
	}
	

	public OutingTimeOfDailyPerformance(String employeeId, GeneralDate ymd, OutingTimeOfDailyAttd outingTime) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.outingTime = outingTime;
	}
}
