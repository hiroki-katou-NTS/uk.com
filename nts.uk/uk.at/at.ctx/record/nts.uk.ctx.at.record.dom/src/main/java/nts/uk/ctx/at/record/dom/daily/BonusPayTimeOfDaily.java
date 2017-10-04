package nts.uk.ctx.at.record.dom.daily;

import java.util.List;

import lombok.Value;

/**
 * 日別実績の加給時間
 * @author keisuke_hoshina
 *
 */
@Value
public class BonusPayTimeOfDaily {
	private List<BonusPayTime> bonusPayTime;
	
	/**
	 * 就内・残業内・休出時間内の加給時間の合計を求める
	 */
	public void calcTotalBonusPayTime() {
		
	}
}
