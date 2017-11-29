package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.childfamilycareset.ShortTimeWorkSetOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.lateleaveearlysetting.LateLeaveEarlySettingOfWorkTime;

/**
 * 就業時間帯の共通設定
 * @author ken_takasu
 *
 */
public class WorkTimeCommonSet {
	@Getter
	private LateLeaveEarlySettingOfWorkTime leaveEarlySetting;
	
	@Getter
	private boolean overDayEndCalcSet;
	
	@Getter
	private ShortTimeWorkSetOfWorkTime shortTimeWorkSet;
	
}
