package nts.uk.ctx.at.shared.dom.worktime.commonsetting.lateleaveearlysetting;

import lombok.Value;

/**
 * 就業時間帯の遅刻・早退設定
 * @author ken_takasu
 *
 */
@Value
public class LateLeaveEarlySettingOfWorkTime {
//	private LateLeaveEarlyEachSettingOfWorkTime lateSettingOfWorkTime;
//	private LateLeaveEarlyEachSettingOfWorkTime leaveEarlyEachSettingOfWorkTime;
	private OtherEmTimezoneLateEarlySet lateSet;
	private OtherEmTimezoneLateEarlySet leaveEarlySet;
	private LateLeaveEarlyCommonSettingOfWorkTime commonSetting;
}



