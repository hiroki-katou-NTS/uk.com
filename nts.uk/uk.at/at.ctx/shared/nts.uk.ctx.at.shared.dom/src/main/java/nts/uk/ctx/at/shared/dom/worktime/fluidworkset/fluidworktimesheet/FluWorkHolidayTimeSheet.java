package nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidworktimesheet;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluidTimeSetting;

/**
 * 流動休出時間帯
 * @author ken_takasu
 *
 */
@Value
public class FluWorkHolidayTimeSheet {
	
	private FluidTimeSetting fluidTimeSetting;
	private HolidayWorkFrameNo withinStatutoryHolidayWorkFrameNo;//法定内休出枠NO
	private HolidayWorkFrameNo excessOfStatutoryHolidayWorkFrameNo;//法定外休出枠NO
	private HolidayWorkFrameNo excessOfStatutorySpecialHolidayFrameNo;//法定外祝日枠NO
	private boolean withinStatutoryHolidayWorkTimeSpentAtWork;//法定内休出を拘束時間として扱う
	private boolean excessOfStatutoryHolidayWorkTimeSpentAtWork;//法定外休出を拘束時間として扱う
	private boolean excessOfStatutorySpecialHolidayTimeSpentAtWork;//法定外祝日を拘束時間として扱う
}
