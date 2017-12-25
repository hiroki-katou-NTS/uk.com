package nts.uk.ctx.at.record.dom.raborstandardact;

import lombok.Value;

/**
 * 半日勤務のフレックス計算方法
 * @author keisuke_hoshina
 *
 */
@Value
public class FlexCalcMethodOfHalfWork {
	private FlexCalcMethodOfEachPremiumHalfWork halfHoliday;
	private FlexCalcMethodOfEachPremiumHalfWork halfCompensatoryHoliday;
}
