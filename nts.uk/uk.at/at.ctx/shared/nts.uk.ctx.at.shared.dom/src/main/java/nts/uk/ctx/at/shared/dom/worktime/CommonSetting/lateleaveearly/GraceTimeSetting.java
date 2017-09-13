package nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly;

import lombok.Value;

/**
 * 猶予時間設定
 * @author ken_takasu
 *
 */
@Value
public class GraceTimeSetting {

	private LateLeaveEarlyGraceTime graceTime;
	private boolean includeInWorkingHours;

}
