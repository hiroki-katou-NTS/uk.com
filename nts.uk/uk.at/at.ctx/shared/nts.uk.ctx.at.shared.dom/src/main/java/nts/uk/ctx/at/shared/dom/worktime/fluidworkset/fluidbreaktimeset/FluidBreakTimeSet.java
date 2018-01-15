package nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset;

import lombok.Value;

/**
 * 流動休憩設定
 * @author keisuke_hoshina
 *
 */
@Value
public class FluidBreakTimeSet {
	private FlowRestCalcMethod calcMethod;
	private boolean conbineStamp;
}
