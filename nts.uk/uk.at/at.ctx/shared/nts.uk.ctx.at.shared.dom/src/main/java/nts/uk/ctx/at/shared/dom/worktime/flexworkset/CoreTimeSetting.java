package nts.uk.ctx.at.shared.dom.worktime.flexworkset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * FlexWork CoreTimeSetting 
 * @author keisuke_hoshina
 *
 */

@Getter
public class CoreTimeSetting {
	
	//**　使用区分   **//
	private UseSetting use;
	
	//** コアタイム時間帯 **//
	private TimeSpanForCalc coreTime;

}
