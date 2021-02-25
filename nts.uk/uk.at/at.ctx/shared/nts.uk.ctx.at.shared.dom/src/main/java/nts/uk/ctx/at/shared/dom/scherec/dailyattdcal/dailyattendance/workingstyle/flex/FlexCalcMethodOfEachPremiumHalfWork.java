package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexCalcAtr;

/**
 * 割増時間別半日勤務のフレックス計算方法
 * @author keisuke_hoshina
 *
 */
@Value
@AllArgsConstructor
public class FlexCalcMethodOfEachPremiumHalfWork {
	private FlexCalcMethod calcPremium;
	private FlexCalcMethod calcLack;
	
	public static FlexCalcMethodOfEachPremiumHalfWork of(FlexCalcAtr premium, FlexCalcAtr lack) {		
		return new FlexCalcMethodOfEachPremiumHalfWork(
				EnumAdaptor.valueOf(premium.value, FlexCalcMethod.class),
				EnumAdaptor.valueOf(lack.value, FlexCalcMethod.class));
	}
}

