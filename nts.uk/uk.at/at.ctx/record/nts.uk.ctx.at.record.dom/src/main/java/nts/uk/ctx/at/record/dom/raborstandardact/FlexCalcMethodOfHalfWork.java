package nts.uk.ctx.at.record.dom.raborstandardact;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 半日勤務のフレックス計算方法
 * @author keisuke_hoshina
 *
 */
@Value
@AllArgsConstructor
public class FlexCalcMethodOfHalfWork {
	private FlexCalcMethodOfEachPremiumHalfWork halfHoliday;
	private FlexCalcMethodOfEachPremiumHalfWork halfCompensatoryHoliday;
}
