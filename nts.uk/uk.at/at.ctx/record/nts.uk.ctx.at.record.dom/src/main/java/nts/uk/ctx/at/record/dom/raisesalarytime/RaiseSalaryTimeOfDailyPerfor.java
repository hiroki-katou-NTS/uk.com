package nts.uk.ctx.at.record.dom.raisesalarytime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.daily.bonuspaytime.BonusPayTime;

/**
 * 
 * @author nampt
 * 日別実績の加給時間
 *
 */
@Getter
@AllArgsConstructor
public class RaiseSalaryTimeOfDailyPerfor {
	
	//加給時間
	private List<BonusPayTime> raisingSalaryTimes;
	
	//特定日加給時間
	private List<BonusPayTime> autoCalRaisingSalarySettings; 
	
	/**
	 * 就内・残業内・休出時間内の加給時間の合計を求める
	 */
	public void calcTotalBonusPayTime() {
		
	}
	
	
}
