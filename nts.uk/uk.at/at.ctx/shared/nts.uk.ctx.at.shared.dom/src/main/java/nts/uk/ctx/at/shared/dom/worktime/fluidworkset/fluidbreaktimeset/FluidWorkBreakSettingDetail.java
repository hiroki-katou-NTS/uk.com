package nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluidPrefixBreakTimeSet;

/**
 * 流動勤務の休憩設定詳細
 * @author ken_takasu
 *
 */
@Value
public class FluidWorkBreakSettingDetail {
	private FluidPrefixBreakTimeSet fluidPrefixBreakTimeSet;
	private FluidBreakTimeSet fluidBreakTimeSet;
	

}
