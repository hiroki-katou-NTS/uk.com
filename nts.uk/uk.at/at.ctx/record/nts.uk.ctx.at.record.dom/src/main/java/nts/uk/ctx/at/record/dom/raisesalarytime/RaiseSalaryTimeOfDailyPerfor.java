package nts.uk.ctx.at.record.dom.raisesalarytime;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalRaisingSalarySetting;

/**
 * 
 * @author nampt
 * 日別実績の加給時間
 *
 */
@Getter
public class RaiseSalaryTimeOfDailyPerfor {
	
	//加給時間
	private List<RaisingSalaryTime> raisingSalaryTimes;
	
	//特定日加給時間
	private List<AutoCalRaisingSalarySetting> autoCalRaisingSalarySettings; 
	
}
