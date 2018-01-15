package nts.uk.ctx.at.shared.dom.worktime.fluidworkset;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidPrefixBreakTimeOfCalcMethod;

/**
 * 流動固定休憩設定
 * @author keisuke_hoshina
 *
 */
@Value
public class FluidPrefixBreakTimeSet {
	private boolean isReferToBreakClockFromMaster;
	private FluidPrefixBreakTimeOfCalcMethod CalcMethod;
	private boolean privateGoOutTreatBreakTime;
	private boolean unionGoOutTreatBreakTime;
}
