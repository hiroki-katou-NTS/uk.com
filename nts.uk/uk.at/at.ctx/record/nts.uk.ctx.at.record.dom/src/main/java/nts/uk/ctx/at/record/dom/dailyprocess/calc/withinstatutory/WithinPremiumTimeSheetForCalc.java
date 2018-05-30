package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 計算用所定内割増時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class WithinPremiumTimeSheetForCalc {
	//時間帯
	private TimeSpanForCalc timeSheet;
	
	/**
	 * Constructor
	 * @param timeSheet
	 */
	public WithinPremiumTimeSheetForCalc(TimeSpanForCalc timeSheet) {
		super();
		this.timeSheet = timeSheet;
	}
}
