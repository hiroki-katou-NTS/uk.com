package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.deviationtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.calculationattribute.enums.DivergenceTimeAttr;

/** 乖離時間の自動計算設定 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AutoCalcSetOfDivergenceTime {

	/** 乖離: boolean */
	private DivergenceTimeAttr divergenceTime;
}
