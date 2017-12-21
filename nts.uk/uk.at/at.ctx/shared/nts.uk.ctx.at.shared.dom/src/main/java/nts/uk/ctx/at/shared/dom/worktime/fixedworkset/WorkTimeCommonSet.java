package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 就業時間帯の共通設定
 * @author ken_takasu
 *
 */
@AllArgsConstructor
public class WorkTimeCommonSet {
	//@Getter
	//private LateLeaveEarlySettingOfWorkTime leaveEarlySetting;
	
	@Getter
	private boolean overDayEndCalcSet;
}
