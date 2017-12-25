package nts.uk.ctx.at.shared.dom.worktime.commonsetting.lateleaveearlysetting;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * 就業時間帯の遅刻・早退別設定
 * @author ken_takasu
 *
 */
@Value
public class OtherEmTimezoneLateEarlySet {
	
	private LateLeaveEarlyClassification lateLeaveEarlyClassification;
	private GraceTimeSetting graceTimeSetting;
	private TimeRoundingSetting ForRecordTimeRoundingSetting;
	private TimeRoundingSetting ForDeductionTimeRoundingSetting;
	
}
