package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly.LateLeaveEarlyClassification;
import nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly.LateLeaveEarlySettingOfWorkTime;

/**
 * 就業時間帯の共通設定
 * @author ken_takasu
 *
 */

@Getter
public class WorkTimeCommonSet {

		private List<LateLeaveEarlySettingOfWorkTime> lateLeaveEarlySettingOfWorkTime;
		
		public GraceTimeSetting getGraceTimeSet(LateLeaveEarlyClassification t) {
			return lateLeaveEarlySettingOfWorkTime.stream().filter(tc -> tc.lateLeaveEarlyDecision(t)).findFirst().get().getGraceTimeSetting();
		}
}
