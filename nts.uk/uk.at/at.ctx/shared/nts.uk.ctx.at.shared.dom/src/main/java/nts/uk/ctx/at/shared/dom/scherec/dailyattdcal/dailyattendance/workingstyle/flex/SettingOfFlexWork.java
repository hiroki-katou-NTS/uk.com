package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * フレックス勤務の設定
 * @author keisuke_hoshina
 *
 */

@Getter
public class SettingOfFlexWork extends AggregateRoot{
	private FlexCalcMethodOfHalfWork flexCalcMethod;

	public SettingOfFlexWork(FlexCalcMethodOfHalfWork flexCalcMethod) {
		super();
		this.flexCalcMethod = flexCalcMethod;
	}
	
	/**
	 * デフォルト値で作成する
	 * @return
	 */
	public static SettingOfFlexWork defaultValue() {
		return new SettingOfFlexWork(new FlexCalcMethodOfHalfWork(
				new FlexCalcMethodOfEachPremiumHalfWork(FlexCalcMethod.OneDay, FlexCalcMethod.OneDay),
				new FlexCalcMethodOfEachPremiumHalfWork(FlexCalcMethod.OneDay, FlexCalcMethod.OneDay)));
	}

}
