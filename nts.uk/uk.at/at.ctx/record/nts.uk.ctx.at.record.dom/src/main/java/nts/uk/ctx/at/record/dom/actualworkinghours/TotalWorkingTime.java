package nts.uk.ctx.at.record.dom.actualworkinghours;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.raisesalarytime.RaiseSalaryTimeOfDailyPerfor;

/**
 * 
 * @author nampt
 * 総労働時間
 *
 */
@Getter
public class TotalWorkingTime {
	
	//加給時間
	private RaiseSalaryTimeOfDailyPerfor raiseSalaryTimeOfDailyPerfor;
	
	//日別実績の外出時間	
	private List<OutingTimeOfDailyPerformance> outingTimeOfDailyPerformance;
	
	// TODO has some class which haven't written

}
