package nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly;

import lombok.Value;

/**
 * 就業時間帯の遅刻・早退共通設定
 * @author keisuke_hoshina
 *
 */
@Value
public class LateLeaveEarlyCommonSettingOfWorkTime {
	private boolean isDeducteFromWorkTime;
}
