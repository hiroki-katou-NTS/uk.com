package nts.uk.ctx.at.record.dom.raborstandardact;

import lombok.Value;

/**
 * 割増時間別半日勤務のフレックス計算方法
 * @author keisuke_hoshina
 *
 */
@Value
public class FlexCalcMethodOfEachPremiumHalfWork {
	private FlexCalcMethod calcPremium;
	private FlexCalcMethod calcLack;
}
