package nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluidTimeSetting;

/**
 * 流動残業時間帯
 * @author ken_takasu
 *
 */
@Value
public class FluidOverTimeWorkSheet {
	//EAではintではなく残業枠型になっています、一時的にintで作成しました。
	private int overWorkTimeNo;
	private FluidTimeSetting fluidWorkTimeSetting;
	
	
}
