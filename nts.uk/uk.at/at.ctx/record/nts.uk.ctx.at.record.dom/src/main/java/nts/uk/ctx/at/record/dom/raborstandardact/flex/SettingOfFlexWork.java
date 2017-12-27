package nts.uk.ctx.at.record.dom.raborstandardact.flex;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethodOfHalfWork;

/**
 * フレックス勤務の設定
 * @author keisuke_hoshina
 *
 */

@Getter
public class SettingOfFlexWork extends AggregateRoot{
	private FlexCalcMethodOfHalfWork flexCalcMethod;

}
